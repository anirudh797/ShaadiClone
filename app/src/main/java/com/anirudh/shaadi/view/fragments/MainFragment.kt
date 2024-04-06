package com.anirudh.shaadi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.anirudh.shaadi.data.model.ProfileInfo
import com.anirudh.shaadi.databinding.FragmentMainBinding
import com.anirudh.shaadi.view.adapters.ProfilesInfoAdapter
import com.anirudh.shaadi.usecase.viewModel.SearchViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var searchViewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel

    private lateinit var profilesInfoAdapter: ProfilesInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel =
            activity?.viewModelStore?.let { ViewModelProvider(it, searchViewModelFactory) }
                ?.get(SearchViewModel::class.java) ?: ViewModelProvider(
                this,
                searchViewModelFactory
            )[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupAdapter()
    }

    private var onAccept: (ProfileInfo) -> Unit = {
        viewModel.userProfileAccepted(it)
    }
    private var onDecline: (ProfileInfo) -> Unit = {
        viewModel.userProfileDeclined(it)
    }

    private fun setupAdapter() {
        profilesInfoAdapter = ProfilesInfoAdapter(requireContext(), onAccept, onDecline)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            adapter = profilesInfoAdapter
        }
    }

    private fun setupObservers() {

        viewModel.profilesList.observe(viewLifecycleOwner) {
            if (it != null) {
                profilesInfoAdapter.updateList(it)
            }
        }

        viewModel.loadingProgressLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.loader.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.loader.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String? = null, param2: String? = null) =
            MainFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
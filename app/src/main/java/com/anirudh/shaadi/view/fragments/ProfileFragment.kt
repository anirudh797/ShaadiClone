package com.anirudh.shaadi.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.anirudh.shaadi.data.entity.ProfileInfo
import com.anirudh.shaadi.data.entity.ProfileStatus
import com.anirudh.shaadi.databinding.FragmentMainBinding
import com.anirudh.shaadi.view.adapters.ProfilesInfoAdapter
import com.anirudh.shaadi.usecase.viewModel.ProfileViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var searchViewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileViewModel

    private lateinit var profilesInfoAdapter: ProfilesInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel =
            activity?.viewModelStore?.let { ViewModelProvider(it, searchViewModelFactory) }
                ?.get(ProfileViewModel::class.java) ?: ViewModelProvider(
                this,
                searchViewModelFactory
            )[ProfileViewModel::class.java]
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
        setupClickListeners()
        viewModel.getProfilesList()
    }

    private fun setupClickListeners() {
        binding.retryBtn.setOnClickListener {
            viewModel.getProfilesList()
        }
        binding.swipe.setOnRefreshListener {
            viewModel.getProfilesList()
        }
    }

    private var onAccept: (ProfileInfo) -> Unit = {
        it.profileStatus = ProfileStatus.ACCEPTED
        viewModel.updateUserProfileInDb(it)
    }
    private var onDecline: (ProfileInfo) -> Unit = {
        it.profileStatus = ProfileStatus.DECLINED
        viewModel.updateUserProfileInDb(it)
    }

    private fun setupAdapter() {
        profilesInfoAdapter = ProfilesInfoAdapter(requireContext(), onAccept, onDecline)
        binding.recyclerView.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            adapter = profilesInfoAdapter
        }
    }

    private fun setupObservers() {

        viewModel.profilesList.observe(viewLifecycleOwner) {
            binding.swipe.isRefreshing = false
            if (!it.isNullOrEmpty()) {
                binding.retryBtn.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                profilesInfoAdapter.updateList(it)
            } else {
                viewModel.handleError()
            }
        }

        viewModel.showError.observe(viewLifecycleOwner) {
            binding.retryBtn.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }

        viewModel.loadingProgressLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.retryBtn.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.loader.visibility = View.VISIBLE
            } else {
                binding.loader.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String? = null, param2: String? = null) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
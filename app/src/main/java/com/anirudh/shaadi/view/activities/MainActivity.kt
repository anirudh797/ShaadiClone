package com.anirudh.shaadi.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anirudh.shaadi.R
import com.anirudh.shaadi.databinding.ActivityMainBinding
import com.anirudh.shaadi.view.fragments.MainFragment
import com.anirudh.shaadi.usecase.viewModel.SearchViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val fragment: MainFragment = MainFragment.newInstance()

        // for passing data to fragment
        val bundle = Bundle()
        fragment.arguments = bundle

        // check is important to prevent activity from attaching the fragment if already its attached
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fcv, fragment, "main_fragment")
                .commit()
        }
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
        setupObservers()
        viewModel.initializeDb(this)
        viewModel.getProfilesList()

    }

    private fun setupClickListeners() {
        binding.btn.setOnClickListener {
//            viewModel.showLastSearchResults()
        }
    }

    private fun setupObservers() {

    }


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector

    }

}

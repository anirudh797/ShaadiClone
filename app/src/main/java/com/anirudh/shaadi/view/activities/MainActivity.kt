package com.anirudh.shaadi.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anirudh.shaadi.R
import com.anirudh.shaadi.databinding.ActivityMainBinding
import com.anirudh.shaadi.view.fragments.ProfileFragment
import com.anirudh.shaadi.view.viewModel.ProfileViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val fragment: ProfileFragment = ProfileFragment.newInstance()

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
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector

    }

}

package com.anirudh.shaadi.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anirudh.shaadi.di.ViewModelKey
import com.anirudh.shaadi.view.viewModel.ProfileViewModel
import com.anirudh.shaadi.view.viewModel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
@Module
public abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    protected abstract fun searchViewModel(profileViewModel: ProfileViewModel): ViewModel

}
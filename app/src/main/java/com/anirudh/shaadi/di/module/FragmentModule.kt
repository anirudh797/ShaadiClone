package com.anirudh.shaadi.di.module

import com.anirudh.shaadi.view.fragments.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): ProfileFragment

}
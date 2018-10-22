package com.dumi.svq_ver10.ui.user

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.dumi.svq_ver10.di.scopes.FragmentScope

@Module
abstract class UserDetailFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [UserDetailModule::class])
    abstract fun contributeUserDetailFragment(): UserDetailFragment
}
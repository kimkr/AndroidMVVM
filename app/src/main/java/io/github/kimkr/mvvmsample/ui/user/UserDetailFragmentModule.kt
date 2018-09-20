package io.github.kimkr.mvvmsample.ui.user

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.kimkr.mvvmsample.di.scopes.FragmentScope

@Module
abstract class UserDetailFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [UserDetailModule::class])
    abstract fun contributeUserDetailFragment(): UserDetailFragment
}
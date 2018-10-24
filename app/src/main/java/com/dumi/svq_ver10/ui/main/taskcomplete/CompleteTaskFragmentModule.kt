package com.dumi.svq_ver10.ui.main.taskcomplete

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompleteTaskFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CompleteTaskModule::class])
    abstract fun contributeCompleteTaskFragment(): CompleteTaskFragment
}
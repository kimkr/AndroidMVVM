package com.dumi.svq_ver10.ui.main.taskincomplete

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class IncompleteTaskFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [IncompleteTaskModule::class])
    abstract fun contributeIncompleteTaskFragment(): IncompleteTaskFragment
}
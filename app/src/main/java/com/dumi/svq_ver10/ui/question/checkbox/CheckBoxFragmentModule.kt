package com.dumi.svq_ver10.ui.question.checkbox

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class CheckBoxFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CheckBoxModule::class])
    abstract fun contributeCheckBoxFragment(): CheckBoxFragment
}
package com.dumi.svq_ver10.ui.question.text

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TextFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [TextModule::class])
    abstract fun contributeTextFragment(): TextFragment
}
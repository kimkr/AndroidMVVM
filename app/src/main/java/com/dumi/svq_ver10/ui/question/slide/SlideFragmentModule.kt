package com.dumi.svq_ver10.ui.question.slide

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class SlideFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SlideModule::class])
    abstract fun contributeSlideFragment(): SlideFragment
}
package com.dumi.svq_ver10.ui.main.setting.interval

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class IntervalFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [IntervalModule::class])
    abstract fun contributeIntervalFragment(): IntervalFragment
}
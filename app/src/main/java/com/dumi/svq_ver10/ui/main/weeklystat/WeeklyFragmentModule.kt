package com.dumi.svq_ver10.ui.main.weeklystat

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WeeklyFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [WeeklyModule::class])
    abstract fun contributeWeeklyFragment(): WeeklyFragment
}
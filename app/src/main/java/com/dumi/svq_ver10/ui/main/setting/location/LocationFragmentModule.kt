package com.dumi.svq_ver10.ui.main.setting.location

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [LocationModule::class])
    abstract fun contributeLocationFragment(): LocationFragment
}
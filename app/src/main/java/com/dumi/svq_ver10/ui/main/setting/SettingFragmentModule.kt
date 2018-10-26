package com.dumi.svq_ver10.ui.main.setting

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun contributeSettingFragment():SettingFragment
}
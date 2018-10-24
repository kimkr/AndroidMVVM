package com.dumi.svq_ver10.ui.main.setting.healing

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HealingFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HealingModule::class])
    abstract fun contributeHealingFragment(): HealingFragment
}
package com.dumi.svq_ver10.ui.main.selfcheck

import com.dumi.svq_ver10.di.scopes.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SelfCheckFragmentModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [SelfCheckModule::class])
    abstract fun contributeSelfCheckFragment(): SelfCheckFragment
}
package com.dumi.svq_ver10.ui.main.setting.healing

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class HealingModule {

    @Provides
    @FragmentScope
    @Named("HealingViewModel")
    fun provideViewModel(fragment: HealingFragment, viewModelFactory: ViewModelFactory): HealingViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(HealingViewModel::class.java)
    }
}
package com.dumi.svq_ver10.ui.main.setting

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SettingModule {

    @Provides
    @FragmentScope
    @Named("SettingViewModel")
    fun provideViewModel(fragment: SettingFragment, viewModelFactory: ViewModelFactory): SettingViewModel{
        return ViewModelProviders.of(fragment, viewModelFactory).get(SettingViewModel::class.java)
    }
}
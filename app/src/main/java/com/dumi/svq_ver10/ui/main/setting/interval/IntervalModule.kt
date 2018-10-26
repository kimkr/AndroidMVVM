package com.dumi.svq_ver10.ui.main.setting.interval

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class IntervalModule {

    @Provides
    @FragmentScope
    @Named("IntervalViewModel")
    fun provideViewModel(fragment: IntervalFragment, viewModelFactory: ViewModelFactory): IntervalViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(IntervalViewModel::class.java)
    }
}
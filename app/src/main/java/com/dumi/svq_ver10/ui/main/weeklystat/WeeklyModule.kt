package com.dumi.svq_ver10.ui.main.weeklystat

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class WeeklyModule {

    @Provides
    @FragmentScope
    @Named("WeeklyViewModel")
    fun provideViewModel(fragment: WeeklyFragment, viewModelFactory: ViewModelFactory): WeeklyViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(WeeklyViewModel::class.java)
    }
}
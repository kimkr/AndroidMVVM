package com.dumi.svq_ver10.ui.main.setting.location

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class LocationModule {

    @Provides
    @FragmentScope
    @Named("LocationViewModel")
    fun provideViewModel(fragment: LocationFragment, viewModelFactory: ViewModelFactory): LocationViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(LocationViewModel::class.java)
    }
}
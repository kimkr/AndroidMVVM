package com.dumi.svq_ver10.ui.main.home

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class HomeModule {

    @Provides
    @FragmentScope
    @Named("HomeViewModel")
    fun provideViewModel(fragment: HomeFragment, viewModelFactory: ViewModelFactory): HomeViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(HomeViewModel::class.java)
    }
}
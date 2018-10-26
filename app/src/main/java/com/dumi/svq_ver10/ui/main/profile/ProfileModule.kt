package com.dumi.svq_ver10.ui.main.profile

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ProfileModule {

    @Provides
    @FragmentScope
    @Named("ProfileViewModel")
    fun provideViewModel(fragment: ProfileFragment, viewModelFactory: ViewModelFactory): ProfileViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(ProfileViewModel::class.java)
    }
}
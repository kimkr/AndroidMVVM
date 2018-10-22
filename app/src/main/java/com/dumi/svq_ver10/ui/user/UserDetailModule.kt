package com.dumi.svq_ver10.ui.user

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import javax.inject.Named

@Module
class UserDetailModule {

    @Provides
    @FragmentScope
    @Named("fragment")
    fun provideViewModel(fragment: UserDetailFragment, viewModelFactory: ViewModelFactory): UserViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(UserViewModel::class.java)
    }
}
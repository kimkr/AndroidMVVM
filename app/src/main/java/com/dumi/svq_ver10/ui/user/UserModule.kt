package com.dumi.svq_ver10.ui.user

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.ui.ViewModelFactory
import javax.inject.Named

@Module
class UserModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(userRepository: UserRepository): ViewModelFactory {
        return ViewModelFactory(userRepository)
    }

    @Provides
    @ActivityScope
    @Named("activity")
    fun provideViewModel(activity: UserActivity, viewModelFactory: ViewModelFactory): UserViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(UserViewModel::class.java)
    }
}
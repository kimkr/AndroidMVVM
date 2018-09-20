package io.github.kimkr.mvvmsample.ui.user

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.persistence.repository.UserRepository
import io.github.kimkr.mvvmsample.ui.ViewModelFactory
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
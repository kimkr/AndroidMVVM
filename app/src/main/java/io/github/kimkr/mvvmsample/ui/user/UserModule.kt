package io.github.kimkr.mvvmsample.ui.user

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.persistence.user.UserDao
import io.github.kimkr.mvvmsample.ui.ViewModelFactory
import javax.inject.Named

@Module
class UserModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(dataSource: UserDao): ViewModelFactory {
        return ViewModelFactory(dataSource)
    }

    @Provides
    @ActivityScope
    @Named("activity")
    fun provideViewModel(activity: UserActivity, viewModelFactory: ViewModelFactory): UserViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(UserViewModel::class.java)
    }
}
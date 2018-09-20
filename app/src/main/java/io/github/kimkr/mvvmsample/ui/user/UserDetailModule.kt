package io.github.kimkr.mvvmsample.ui.user

import android.arch.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.di.scopes.FragmentScope
import io.github.kimkr.mvvmsample.ui.ViewModelFactory
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
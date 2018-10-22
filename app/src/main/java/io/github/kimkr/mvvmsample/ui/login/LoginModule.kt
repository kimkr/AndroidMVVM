package io.github.kimkr.mvvmsample.ui.login

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope

@Module
class LoginModule {

    @Provides
    @ActivityScope
    fun provideActivityAsContext(activity: LoginActivity): Context {
        return activity
    }
}
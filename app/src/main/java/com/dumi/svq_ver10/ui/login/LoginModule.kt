package com.dumi.svq_ver10.ui.login

import android.content.Context
import dagger.Module
import dagger.Provides
import com.dumi.svq_ver10.di.scopes.ActivityScope

@Module
class LoginModule {

    @Provides
    @ActivityScope
    fun provideActivityAsContext(activity: LoginActivity): Context {
        return activity
    }
}
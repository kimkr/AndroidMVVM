package com.dumi.svq_ver10.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import com.dumi.svq_ver10.App
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}
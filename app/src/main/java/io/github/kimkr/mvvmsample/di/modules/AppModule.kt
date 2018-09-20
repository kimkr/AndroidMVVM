package io.github.kimkr.mvvmsample.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.App
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}
package io.github.kimkr.mvvmsample.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import io.github.kimkr.mvvmsample.App
import io.github.kimkr.mvvmsample.di.modules.ActivityModule
import io.github.kimkr.mvvmsample.di.modules.AppModule
import io.github.kimkr.mvvmsample.di.modules.DataModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, DataModule::class,
    ActivityModule::class])
interface AppComponent {
    fun inject(app: App)
}
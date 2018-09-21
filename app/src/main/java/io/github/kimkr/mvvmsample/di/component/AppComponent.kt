package io.github.kimkr.mvvmsample.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import io.github.kimkr.mvvmsample.App
import io.github.kimkr.mvvmsample.di.modules.ActivityModule
import io.github.kimkr.mvvmsample.di.modules.AppModule
import io.github.kimkr.mvvmsample.persistence.db.RepositoryModule
import io.github.kimkr.mvvmsample.persistence.di.APIModule
import io.github.kimkr.mvvmsample.persistence.di.DBModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, DBModule::class,
    APIModule::class, RepositoryModule::class, ActivityModule::class])
interface AppComponent {
    fun inject(app: App)
}
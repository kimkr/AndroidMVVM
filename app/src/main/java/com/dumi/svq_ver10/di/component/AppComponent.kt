package com.dumi.svq_ver10.di.component

import com.dumi.svq_ver10.App
import com.dumi.svq_ver10.di.modules.ActivityModule
import com.dumi.svq_ver10.di.modules.AppModule
import com.dumi.svq_ver10.di.modules.ServiceModule
import com.dumi.svq_ver10.persistence.db.RepositoryModule
import com.dumi.svq_ver10.persistence.di.APIModule
import com.dumi.svq_ver10.persistence.di.DBModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, DBModule::class,
    APIModule::class, RepositoryModule::class, ActivityModule::class, ServiceModule::class])
interface AppComponent {
    fun inject(app: App)
}
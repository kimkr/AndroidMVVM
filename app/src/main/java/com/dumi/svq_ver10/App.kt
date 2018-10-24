package com.dumi.svq_ver10

import android.app.Activity
import android.app.Application
import android.app.Service
import com.dumi.svq_ver10.di.component.DaggerAppComponent
import com.dumi.svq_ver10.di.modules.AppModule
import com.dumi.svq_ver10.persistence.di.APIModule
import com.dumi.svq_ver10.persistence.di.DBModule
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dBModule(DBModule(this))
                .aPIModule(APIModule(this))
                .build().inject(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
}
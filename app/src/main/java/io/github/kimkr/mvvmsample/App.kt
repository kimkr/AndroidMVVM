package io.github.kimkr.mvvmsample

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.github.kimkr.mvvmsample.di.component.DaggerAppComponent
import io.github.kimkr.mvvmsample.di.modules.AppModule
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}
package com.dumi.svq_ver10.di.modules

import com.dumi.svq_ver10.service.FirebaseMsgService
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract fun contributeFirebaseMsgService(): FirebaseMsgService
}
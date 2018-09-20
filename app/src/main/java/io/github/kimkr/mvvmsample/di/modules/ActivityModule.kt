package io.github.kimkr.mvvmsample.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.kimkr.mvvmsample.ui.UserActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeUserActivity(): UserActivity
}
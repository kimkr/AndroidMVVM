package io.github.kimkr.mvvmsample.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.kimkr.mvvmsample.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
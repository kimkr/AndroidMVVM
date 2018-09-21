package io.github.kimkr.mvvmsample.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.ui.post.PostActivity
import io.github.kimkr.mvvmsample.ui.post.PostModule
import io.github.kimkr.mvvmsample.ui.user.UserActivity
import io.github.kimkr.mvvmsample.ui.user.UserDetailFragmentModule
import io.github.kimkr.mvvmsample.ui.user.UserModule

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [UserModule::class, UserDetailFragmentModule::class])
    abstract fun contributeUserActivity(): UserActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PostModule::class])
    abstract fun contributePostActivity(): PostActivity
}
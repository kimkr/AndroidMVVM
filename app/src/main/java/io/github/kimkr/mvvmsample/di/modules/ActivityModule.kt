package io.github.kimkr.mvvmsample.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.ui.location.LocationActivity
import io.github.kimkr.mvvmsample.ui.login.LoginActivity
import io.github.kimkr.mvvmsample.ui.login.LoginModule
import io.github.kimkr.mvvmsample.ui.post.PostActivity
import io.github.kimkr.mvvmsample.ui.post.PostModule
import io.github.kimkr.mvvmsample.ui.user.UserActivity
import io.github.kimkr.mvvmsample.ui.user.UserDetailFragmentModule
import io.github.kimkr.mvvmsample.ui.user.UserModule
import io.github.kimkr.mvvmsample.util.location.LocationModule

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [UserModule::class, UserDetailFragmentModule::class])
    abstract fun contributeUserActivity(): UserActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PostModule::class])
    abstract fun contributePostActivity(): PostActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LocationModule::class])
    abstract fun contributeLocationActivity(): LocationActivity
}
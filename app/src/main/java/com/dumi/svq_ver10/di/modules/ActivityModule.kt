package com.dumi.svq_ver10.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.ui.location.LocationActivity
import com.dumi.svq_ver10.ui.login.LoginActivity
import com.dumi.svq_ver10.ui.login.LoginModule
import com.dumi.svq_ver10.ui.post.PostActivity
import com.dumi.svq_ver10.ui.post.PostModule
import com.dumi.svq_ver10.ui.user.UserActivity
import com.dumi.svq_ver10.ui.user.UserDetailFragmentModule
import com.dumi.svq_ver10.ui.user.UserModule
import com.dumi.svq_ver10.util.location.LocationModule

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
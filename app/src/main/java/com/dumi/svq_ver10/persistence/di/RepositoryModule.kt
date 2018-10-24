package com.dumi.svq_ver10.persistence.db

import com.dumi.svq_ver10.di.qualifier.Cache
import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.di.qualifier.Remote
import com.dumi.svq_ver10.persistence.cache.PostCache
import com.dumi.svq_ver10.persistence.cache.PostService
import com.dumi.svq_ver10.persistence.cache.UserCache
import com.dumi.svq_ver10.persistence.cache.UserService
import com.dumi.svq_ver10.persistence.sources.PostDataSource
import com.dumi.svq_ver10.persistence.sources.TaskDataSource
import com.dumi.svq_ver10.persistence.sources.UserDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    @Local
    abstract fun provideLocalUserDatabase(userDao: UserDao): UserDataSource

    @Binds
    @Cache
    abstract fun provideCacheUserDatabase(userCache: UserCache): UserDataSource

    @Binds
    @Remote
    abstract fun provideRemoteUserDatabase(userService: UserService): UserDataSource

    @Binds
    @Local
    abstract fun provideLocalPostDatabase(postDao: PostDao): PostDataSource

    @Binds
    @Cache
    abstract fun provideCachePostDatabase(postCache: PostCache): PostDataSource

    @Binds
    @Remote
    abstract fun provideRemotePostDatabase(postService: PostService): PostDataSource

    @Binds
    @Local
    abstract fun provideLocalTaskDatabase(taskDao: TaskDao): TaskDataSource
}
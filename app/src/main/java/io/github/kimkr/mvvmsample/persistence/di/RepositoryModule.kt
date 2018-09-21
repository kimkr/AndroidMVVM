package io.github.kimkr.mvvmsample.persistence.db

import dagger.Binds
import dagger.Module
import io.github.kimkr.mvvmsample.di.qualifier.Cache
import io.github.kimkr.mvvmsample.di.qualifier.Local
import io.github.kimkr.mvvmsample.di.qualifier.Remote
import io.github.kimkr.mvvmsample.persistence.cache.PostCache
import io.github.kimkr.mvvmsample.persistence.cache.PostService
import io.github.kimkr.mvvmsample.persistence.cache.UserAPI
import io.github.kimkr.mvvmsample.persistence.cache.UserCache
import io.github.kimkr.mvvmsample.persistence.sources.PostDataSource
import io.github.kimkr.mvvmsample.persistence.sources.UserDataSource

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
    abstract fun provideRemoteUserDatabase(userAPI: UserAPI): UserDataSource

    @Binds
    @Local
    abstract fun provideLocalPostDatabase(postDao: PostDao): PostDataSource

    @Binds
    @Cache
    abstract fun provideCachePostDatabase(postCache: PostCache): PostDataSource

    @Binds
    @Remote
    abstract fun provideRemotePostDatabase(postService: PostService): PostDataSource
}
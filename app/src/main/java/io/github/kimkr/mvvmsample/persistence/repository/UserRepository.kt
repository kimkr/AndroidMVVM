package io.github.kimkr.mvvmsample.persistence.repository

import io.github.kimkr.mvvmsample.di.qualifier.Cache
import io.github.kimkr.mvvmsample.di.qualifier.Local
import io.github.kimkr.mvvmsample.di.qualifier.Remote
import io.github.kimkr.mvvmsample.persistence.model.User
import io.github.kimkr.mvvmsample.persistence.sources.UserDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(@Local private val localDataSource: UserDataSource,
                                         @Remote private val remoteDataSource: UserDataSource,
                                         @Cache private val cacheDataSource: UserDataSource)
    : UserDataSource, Repository {

    override fun getUserById(id: String): Flowable<User> {
        return localDataSource.getUserById(id)
    }

    override fun insertUser(user: User) {
        localDataSource.insertUser(user)
    }

    override fun deleteAllUsers() {
        deleteAllUsers()
    }
}
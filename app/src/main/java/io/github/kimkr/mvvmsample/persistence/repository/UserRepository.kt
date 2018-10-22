package io.github.kimkr.mvvmsample.persistence.repository

import io.github.kimkr.mvvmsample.di.qualifier.Cache
import io.github.kimkr.mvvmsample.di.qualifier.Local
import io.github.kimkr.mvvmsample.di.qualifier.Remote
import io.github.kimkr.mvvmsample.persistence.model.User
import io.github.kimkr.mvvmsample.persistence.sources.UserDataSource
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(@Local private val localDataSource: UserDataSource,
                                         @Remote private val remoteDataSource: UserDataSource,
                                         @Cache private val cacheDataSource: UserDataSource)
    : Repository {

    fun getUserById(id: String): Maybe<User> {
        return remoteDataSource.getUserById(id)
                .flatMap { user ->
                    localDataSource.insertUser(user)
                    Maybe.just(user)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertUser(user: User): Completable {
        return Completable.fromAction { localDataSource.insertUser(user) }
    }

    fun deleteAllUsers(): Completable {
        return Completable.fromAction { deleteAllUsers() }
    }
}
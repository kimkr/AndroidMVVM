package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.di.qualifier.Cache
import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.di.qualifier.Remote
import com.dumi.svq_ver10.persistence.model.User
import com.dumi.svq_ver10.persistence.sources.UserDataSource
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
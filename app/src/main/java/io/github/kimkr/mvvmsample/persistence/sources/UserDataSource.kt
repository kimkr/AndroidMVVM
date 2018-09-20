package io.github.kimkr.mvvmsample.persistence.sources

import io.github.kimkr.mvvmsample.persistence.model.User
import io.reactivex.Flowable

interface UserDataSource {

    fun getUserById(id: String): Flowable<User>

    fun insertUser(user: User)

    fun deleteAllUsers()
}
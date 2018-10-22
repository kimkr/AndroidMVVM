package io.github.kimkr.mvvmsample.persistence.sources

import io.github.kimkr.mvvmsample.persistence.model.User
import io.reactivex.Maybe

interface UserDataSource {

    fun getUserById(id: String): Maybe<User>

    fun insertUser(user: User)

    fun deleteAllUsers()
}
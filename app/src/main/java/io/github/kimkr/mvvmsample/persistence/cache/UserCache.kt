package io.github.kimkr.mvvmsample.persistence.cache

import io.github.kimkr.mvvmsample.persistence.model.User
import io.github.kimkr.mvvmsample.persistence.sources.UserDataSource
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCache @Inject constructor() : UserDataSource {

    override fun getUserById(id: String): Maybe<User> {
        return Maybe.empty()
    }

    override fun insertUser(user: User) {
    }

    override fun deleteAllUsers() {
    }
}
package com.dumi.svq_ver10.persistence.cache

import com.dumi.svq_ver10.persistence.model.User
import com.dumi.svq_ver10.persistence.sources.UserDataSource
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCache @Inject constructor() : UserDataSource {
    override fun getUser(): Maybe<User> {
        return Maybe.empty()
    }

    override fun getUserById(id: String): Maybe<User> {
        return Maybe.empty()
    }

    override fun insertUser(user: User) {
    }

    override fun deleteAllUsers() {
    }
}
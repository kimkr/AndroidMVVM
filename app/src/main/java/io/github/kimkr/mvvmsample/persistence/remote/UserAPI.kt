package io.github.kimkr.mvvmsample.persistence.cache

import io.github.kimkr.mvvmsample.persistence.model.User
import io.github.kimkr.mvvmsample.persistence.sources.UserDataSource
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAPI @Inject constructor() : UserDataSource {

    override fun getUserById(id: String): Flowable<User> {
        return Flowable.empty()
    }

    override fun insertUser(user: User) {
    }

    override fun deleteAllUsers() {
    }
}
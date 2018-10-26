package com.dumi.svq_ver10.persistence.sources

import com.dumi.svq_ver10.persistence.model.User
import io.reactivex.Maybe

interface UserDataSource {

    fun getUserById(id: String): Maybe<User>

    fun insertUser(user: User)

    fun updateUser(user: User)

    fun deleteAllUsers()

    fun getUser(): Maybe<User>
}
package com.dumi.svq_ver10.ui.user

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.model.Gender
import com.dumi.svq_ver10.persistence.model.User
import com.dumi.svq_ver10.persistence.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Maybe

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun userName(): Maybe<String> {
        return userRepository.getUserById(USER_ID)
                .map { user -> user.name }
    }

    fun updateUserName(userName: String): Completable {
        return Completable.fromAction {
            val user = User(USER_ID, userName, "", "", "",
                    Gender.MALE, "", true)
            userRepository.insertUser(user)
        }
    }

    companion object {
        const val USER_ID = "TEST_USER_ID"
    }
}
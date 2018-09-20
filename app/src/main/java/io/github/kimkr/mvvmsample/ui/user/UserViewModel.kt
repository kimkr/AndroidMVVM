package io.github.kimkr.mvvmsample.ui.user

import android.arch.lifecycle.ViewModel
import io.github.kimkr.mvvmsample.persistence.model.User
import io.github.kimkr.mvvmsample.persistence.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun userName(): Flowable<String> {
        return userRepository.getUserById(USER_ID)
                .map { user -> user.userName }
    }

    fun updateUserName(userName: String): Completable {
        return Completable.fromAction {
            val user = User(USER_ID, userName)
            userRepository.insertUser(user)
        }
    }

    companion object {
        const val USER_ID = "TEST_USER_ID"
    }
}
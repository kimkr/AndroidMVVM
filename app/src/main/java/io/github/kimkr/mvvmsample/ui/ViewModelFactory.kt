package io.github.kimkr.mvvmsample.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.github.kimkr.mvvmsample.persistence.repository.Repository
import io.github.kimkr.mvvmsample.persistence.repository.UserRepository
import io.github.kimkr.mvvmsample.ui.user.UserViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository as UserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
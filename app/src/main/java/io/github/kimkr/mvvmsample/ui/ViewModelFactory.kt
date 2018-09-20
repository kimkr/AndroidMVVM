package io.github.kimkr.mvvmsample.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.github.kimkr.mvvmsample.persistence.UserDao

class ViewModelFactory(private val dataSource: UserDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
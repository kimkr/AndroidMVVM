package com.dumi.svq_ver10.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dumi.svq_ver10.persistence.repository.Repository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.ui.main.home.HomeViewModel
import com.dumi.svq_ver10.ui.user.UserViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository as UserRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository as TaskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
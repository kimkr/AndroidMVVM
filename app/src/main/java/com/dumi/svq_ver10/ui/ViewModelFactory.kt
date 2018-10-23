package com.dumi.svq_ver10.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dumi.svq_ver10.persistence.repository.SettingRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.ui.main.home.HomeViewModel
import com.dumi.svq_ver10.ui.main.setting.SettingViewModel
import com.dumi.svq_ver10.ui.main.weeklystat.WeeklyViewModel
import com.dumi.svq_ver10.ui.user.UserViewModel

class ViewModelFactory(private val userRepository: UserRepository,
                       private val taskRepository: TaskRepository,
                       private val settingRepository: SettingRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(taskRepository) as T
        } else if (modelClass.isAssignableFrom(WeeklyViewModel::class.java)) {
            return WeeklyViewModel(taskRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(settingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
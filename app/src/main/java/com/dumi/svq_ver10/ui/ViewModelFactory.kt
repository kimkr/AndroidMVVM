package com.dumi.svq_ver10.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dumi.svq_ver10.persistence.repository.*
import com.dumi.svq_ver10.ui.main.home.HomeViewModel
import com.dumi.svq_ver10.ui.main.profile.ProfileViewModel
import com.dumi.svq_ver10.ui.main.selfcheck.SelfCheckViewModel
import com.dumi.svq_ver10.ui.main.setting.SettingViewModel
import com.dumi.svq_ver10.ui.main.setting.healing.HealingViewModel
import com.dumi.svq_ver10.ui.main.setting.interval.IntervalViewModel
import com.dumi.svq_ver10.ui.main.setting.location.LocationViewModel
import com.dumi.svq_ver10.ui.main.taskcomplete.CompleteTaskViewModel
import com.dumi.svq_ver10.ui.main.taskincomplete.IncompleteTaskViewModel
import com.dumi.svq_ver10.ui.main.weeklystat.WeeklyViewModel
import com.dumi.svq_ver10.ui.question.checkbox.CheckBoxViewModel
import com.dumi.svq_ver10.ui.question.slide.SlideViewModel
import com.dumi.svq_ver10.ui.question.text.TextViewModel
import com.dumi.svq_ver10.ui.user.UserViewModel

class ViewModelFactory(private val userRepository: UserRepository,
                       private val taskRepository: TaskRepository,
                       private val settingRepository: SettingRepository,
                       private val locationRepository: LocationRepository,
                       private val questionRepository: QuestionRepository)
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
        } else if (modelClass.isAssignableFrom(SelfCheckViewModel::class.java)) {
            return SelfCheckViewModel(taskRepository) as T
        } else if (modelClass.isAssignableFrom(IncompleteTaskViewModel::class.java)) {
            return IncompleteTaskViewModel(taskRepository) as T
        } else if (modelClass.isAssignableFrom(CompleteTaskViewModel::class.java)) {
            return CompleteTaskViewModel(taskRepository) as T
        } else if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(settingRepository) as T
        } else if (modelClass.isAssignableFrom(IntervalViewModel::class.java)) {
            return IntervalViewModel(settingRepository) as T
        } else if (modelClass.isAssignableFrom(HealingViewModel::class.java)) {
            return HealingViewModel(settingRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository, locationRepository,
                    taskRepository, questionRepository, settingRepository) as T
        } else if (modelClass.isAssignableFrom(TextViewModel::class.java)) {
            return TextViewModel(questionRepository, taskRepository) as T
        } else if (modelClass.isAssignableFrom(SlideViewModel::class.java)) {
            return SlideViewModel(questionRepository, taskRepository) as T
        } else if (modelClass.isAssignableFrom(CheckBoxViewModel::class.java)) {
            return CheckBoxViewModel(questionRepository, taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
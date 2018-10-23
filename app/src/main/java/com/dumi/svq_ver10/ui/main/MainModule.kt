package com.dumi.svq_ver10.ui.main

import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.SettingRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class MainModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(userRepository: UserRepository,
                                taskRepository: TaskRepository,
                                settingRepository: SettingRepository): ViewModelFactory {
        return ViewModelFactory(userRepository, taskRepository, settingRepository)
    }
}
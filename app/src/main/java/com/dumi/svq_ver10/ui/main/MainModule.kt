package com.dumi.svq_ver10.ui.main

import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.*
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class MainModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(userRepository: UserRepository,
                                taskRepository: TaskRepository,
                                settingRepository: SettingRepository,
                                locationRepository: LocationRepository,
                                questionRepository: QuestionRepository): ViewModelFactory {
        return ViewModelFactory(userRepository, taskRepository, settingRepository, locationRepository,
                questionRepository)
    }
}
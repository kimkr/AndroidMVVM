package com.dumi.svq_ver10.ui.question

import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.*
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class QuestionModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(userRepository: UserRepository,
                                taskRepository: TaskRepository,
                                questionRepository: QuestionRepository,
                                settingRepository: SettingRepository,
                                locationRepository: LocationRepository): ViewModelFactory {
        return ViewModelFactory(userRepository, taskRepository, settingRepository, locationRepository,
                questionRepository)
    }
}
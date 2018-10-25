package com.dumi.svq_ver10.ui.user

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.*
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UserModule {

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

    @Provides
    @ActivityScope
    @Named("activity")
    fun provideViewModel(activity: UserActivity, viewModelFactory: ViewModelFactory): UserViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(UserViewModel::class.java)
    }
}
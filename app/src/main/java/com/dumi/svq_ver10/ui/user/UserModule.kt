package com.dumi.svq_ver10.ui.user

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.LocationRepository
import com.dumi.svq_ver10.persistence.repository.SettingRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
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
                                locationRepository: LocationRepository): ViewModelFactory {
        return ViewModelFactory(userRepository, taskRepository, settingRepository, locationRepository)
    }

    @Provides
    @ActivityScope
    @Named("activity")
    fun provideViewModel(activity: UserActivity, viewModelFactory: ViewModelFactory): UserViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(UserViewModel::class.java)
    }
}
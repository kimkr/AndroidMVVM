package com.dumi.svq_ver10.ui.main

import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class MainModule {

    @Provides
    @ActivityScope
    fun provideViewModelFactory(taskRepository: TaskRepository): ViewModelFactory {
        return ViewModelFactory(taskRepository)
    }
}
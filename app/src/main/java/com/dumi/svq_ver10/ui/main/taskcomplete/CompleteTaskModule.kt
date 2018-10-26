package com.dumi.svq_ver10.ui.main.taskcomplete

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CompleteTaskModule {

    @Provides
    @FragmentScope
    @Named("CompleteTaskViewModel")
    fun provideViewModel(fragment: CompleteTaskFragment, viewModelFactory: ViewModelFactory)
            : CompleteTaskViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(CompleteTaskViewModel::class.java)
    }
}
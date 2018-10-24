package com.dumi.svq_ver10.ui.main.taskincomplete

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class IncompleteTaskModule {

    @Provides
    @FragmentScope
    @Named("IncompleteTaskViewModel")
    fun provideViewModel(fragment: IncompleteTaskFragment, viewModelFactory: ViewModelFactory)
            : IncompleteTaskViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(IncompleteTaskViewModel::class.java)
    }
}
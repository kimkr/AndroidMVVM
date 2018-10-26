package com.dumi.svq_ver10.ui.question.checkbox

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CheckBoxModule {

    @Provides
    @FragmentScope
    @Named("CheckBoxViewModel")
    fun provideViewModel(fragment: CheckBoxFragment, viewModelFactory: ViewModelFactory)
            : CheckBoxViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(CheckBoxViewModel::class.java)
    }
}
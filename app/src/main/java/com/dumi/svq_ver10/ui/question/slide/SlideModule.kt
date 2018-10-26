package com.dumi.svq_ver10.ui.question.slide

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SlideModule {

    @Provides
    @FragmentScope
    @Named("SlideViewModel")
    fun provideViewModel(fragment: SlideFragment, viewModelFactory: ViewModelFactory)
            : SlideViewModel {
        return ViewModelProviders.of(fragment, viewModelFactory).get(SlideViewModel::class.java)
    }
}
package com.dumi.svq_ver10.ui.question.text

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class TextModule{

    @Provides
    @FragmentScope
    @Named("TextViewModel")
    fun provideViewModel(fragment: TextFragment, viewModelFactory: ViewModelFactory)
            : TextViewModel{
        return ViewModelProviders.of(fragment, viewModelFactory).get(TextViewModel::class.java)
    }
}
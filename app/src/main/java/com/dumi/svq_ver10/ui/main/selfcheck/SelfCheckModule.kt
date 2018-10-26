package com.dumi.svq_ver10.ui.main.selfcheck

import android.arch.lifecycle.ViewModelProviders
import com.dumi.svq_ver10.di.scopes.FragmentScope
import com.dumi.svq_ver10.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SelfCheckModule {

    @Provides
    @FragmentScope
    @Named("SelfCheckViewModel")
    fun provideViewModel(fragment: SelfCheckFragment, viewModelFactory: ViewModelFactory): SelfCheckViewModel{
        return ViewModelProviders.of(fragment, viewModelFactory).get(SelfCheckViewModel::class.java)
    }
}
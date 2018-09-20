package io.github.kimkr.mvvmsample.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.persistence.UserDao
import io.github.kimkr.mvvmsample.persistence.UserDatabase
import io.github.kimkr.mvvmsample.ui.ViewModelFactory
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDao {
        val database = UserDatabase.getInstance(context)
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(dataSource: UserDao): ViewModelFactory {
        return ViewModelFactory(dataSource)
    }
}
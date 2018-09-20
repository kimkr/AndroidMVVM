package io.github.kimkr.mvvmsample.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.persistence.user.UserDao
import io.github.kimkr.mvvmsample.persistence.user.UserDatabase
import javax.inject.Singleton

@Module
class DataModule(val context: Context) {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDao {
        val database = UserDatabase.getInstance(context)
        return database.userDao()
    }
}
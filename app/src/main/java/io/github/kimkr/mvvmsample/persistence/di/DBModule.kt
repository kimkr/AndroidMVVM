package io.github.kimkr.mvvmsample.persistence.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.persistence.db.LocalDatabase
import io.github.kimkr.mvvmsample.persistence.db.PostDao
import io.github.kimkr.mvvmsample.persistence.db.UserDao
import javax.inject.Singleton

@Module
class DBModule(val context: Context) {

    @Provides
    @Singleton
    fun provideLocalDatabase(): LocalDatabase {
        return LocalDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(database: LocalDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun providePostDataSource(database: LocalDatabase): PostDao {
        return database.postDao()
    }
}
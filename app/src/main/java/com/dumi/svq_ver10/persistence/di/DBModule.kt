package com.dumi.svq_ver10.persistence.di

import android.content.Context
import com.dumi.svq_ver10.persistence.db.*
import dagger.Module
import dagger.Provides
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

    @Provides
    @Singleton
    fun provideTaskDataSource(database: LocalDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideQuestionDataSource(database: LocalDatabase): QuestionDao {
        return database.questionDao()
    }
}
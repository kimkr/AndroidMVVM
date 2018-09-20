package io.github.kimkr.mvvmsample.persistence.db

import android.content.Context
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
}
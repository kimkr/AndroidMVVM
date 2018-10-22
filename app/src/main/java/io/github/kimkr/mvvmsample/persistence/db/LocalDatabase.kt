package io.github.kimkr.mvvmsample.persistence.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import io.github.kimkr.mvvmsample.persistence.converter.DateConverter
import io.github.kimkr.mvvmsample.persistence.converter.GenderConverter
import io.github.kimkr.mvvmsample.persistence.model.Post
import io.github.kimkr.mvvmsample.persistence.model.User

@Database(entities = arrayOf(User::class, Post::class), version = 1)
@TypeConverters(DateConverter::class, GenderConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        LocalDatabase::class.java, "Sample.db")
                        .build()
    }
}
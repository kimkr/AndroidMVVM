package com.dumi.svq_ver10.persistence.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.dumi.svq_ver10.persistence.converter.DateConverter
import com.dumi.svq_ver10.persistence.converter.GenderConverter
import com.dumi.svq_ver10.persistence.model.Post
import com.dumi.svq_ver10.persistence.model.User

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
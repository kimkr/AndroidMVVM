package io.github.kimkr.mvvmsample.persistence.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.github.kimkr.mvvmsample.persistence.model.User
import io.github.kimkr.mvvmsample.persistence.sources.UserDataSource
import io.reactivex.Maybe

@Dao
interface UserDao : UserDataSource {
    @Query("SELECT * FROM Users WHERE userid = :id")
    override fun getUserById(id: String): Maybe<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertUser(user: User)

    @Query("DELETE FROM Users")
    override fun deleteAllUsers()
}
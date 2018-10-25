package com.dumi.svq_ver10.persistence.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dumi.svq_ver10.persistence.model.User
import com.dumi.svq_ver10.persistence.sources.UserDataSource
import io.reactivex.Maybe

@Dao
interface UserDao : UserDataSource {
    @Query("SELECT * FROM Users WHERE userid = :id")
    override fun getUserById(id: String): Maybe<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun updateUser(user: User)

    @Query("DELETE FROM Users")
    override fun deleteAllUsers()

    @Query("SELECT * FROM Users LIMIT 1")
    override fun getUser(): Maybe<User>
}
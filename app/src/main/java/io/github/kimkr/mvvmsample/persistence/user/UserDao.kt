package io.github.kimkr.mvvmsample.persistence.user

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM Users WHERE userid = :id")
    fun getUserById(id: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM Users")
    fun deleteAllUsers()
}
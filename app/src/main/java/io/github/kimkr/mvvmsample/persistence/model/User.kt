package io.github.kimkr.mvvmsample.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User(@PrimaryKey
                @ColumnInfo(name = "userid")
                val id: String = UUID.randomUUID().toString(),
                @ColumnInfo(name = "username")
                val name: String,
                @ColumnInfo(name = "email")
                val email: String,
                @ColumnInfo(name = "phone")
                val phone: String,
                @ColumnInfo(name = "dob")
                val dob: String,
                @ColumnInfo(name = "gender")
                val gender: Gender,
                @ColumnInfo(name = "manager")
                val manager: String,
                @ColumnInfo(name = "autologin")
                val autoLogin: Boolean)

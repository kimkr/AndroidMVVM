package io.github.kimkr.mvvmsample.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "posts")
data class Post(@PrimaryKey
                @ColumnInfo(name = "id")
                val id: String = UUID.randomUUID().toString(),
                @ColumnInfo(name = "author")
                val author: String,
                @ColumnInfo(name = "title")
                val title: String,
                @ColumnInfo(name = "description")
                val description: String,
                @ColumnInfo(name = "url")
                val url: String,
                @ColumnInfo(name = "image")
                val urlToImage: String,
                @ColumnInfo(name = "created")
                val publishedAt: String,
                @ColumnInfo(name = "content")
                val content: String)
//@ColumnInfo(name = "source")
//                val source: String,

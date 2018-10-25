package com.dumi.svq_ver10.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(@PrimaryKey
                    @ColumnInfo(name = "id")
                    var code: String,
                    @ColumnInfo(name = "tree_id")
                    var treeId: String,
                    @ColumnInfo(name = "type")
                    var type: String,
                    @ColumnInfo(name = "time")
                    var time: String,
                    @ColumnInfo(name = "content")
                    var content: String,
                    @ColumnInfo(name = "num")
                    var num: String,
                    @ColumnInfo(name = "left")
                    var left: String,
                    @ColumnInfo(name = "right")
                    var right: String,
                    @ColumnInfo(name = "values")
                    var values: List<String>,
                    @ColumnInfo(name = "task")
                    var task: String)
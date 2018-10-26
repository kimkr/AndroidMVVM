package com.dumi.svq_ver10.persistence.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task(@PrimaryKey
                @ColumnInfo(name = "id")
                val id: String = UUID.randomUUID().toString(),
                @ColumnInfo(name = "consultant")
                val consultant: String?,
                @ColumnInfo(name = "client")
                val client: String,
                @ColumnInfo(name = "question")
                val question: String,
                @ColumnInfo(name = "answer")
                val answer: String?,
                @ColumnInfo(name = "next_task")
                val next: String?,
                @ColumnInfo(name = "assigned_at")
                val assignedAt: Date = Date(),
                @ColumnInfo(name = "answered_at")
                val answeredAt: Date?)

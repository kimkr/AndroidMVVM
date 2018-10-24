package com.dumi.svq_ver10.persistence.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dumi.svq_ver10.persistence.model.Task
import com.dumi.svq_ver10.persistence.sources.TaskDataSource
import io.reactivex.Single

@Dao
interface TaskDao : TaskDataSource {

    @Query("SELECT * FROM Tasks WHERE answer IS NULL")
    override fun getAllIncompleteTasks(): Single<List<Task>>

    @Query("SELECT * FROM Tasks WHERE (answered_at IS NOT NULL) AND (answered_at BETWEEN :start AND :end) ORDER BY answered_at")
    override fun getCompleteTaskBetween(start: Long, end: Long): Single<List<Task>>
}
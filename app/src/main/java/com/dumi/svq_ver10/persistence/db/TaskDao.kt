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
}
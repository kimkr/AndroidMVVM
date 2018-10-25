package com.dumi.svq_ver10.persistence.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dumi.svq_ver10.persistence.model.Task
import com.dumi.svq_ver10.persistence.sources.TaskDataSource
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TaskDao : TaskDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertTask(task: Task)

    @Query("UPDATE Tasks SET answer = :answer, answered_at = strftime('%s', 'now') WHERE id = :id")
    override fun updateAnswer(id: String, answer: String): Long

    @Query("SELECT * FROM Tasks WHERE answer IS NULL")
    override fun getAllIncompleteTasks(): Single<List<Task>>

    @Query("SELECT * FROM Tasks WHERE (answered_at IS NOT NULL) AND (answered_at BETWEEN :start AND :end) ORDER BY answered_at")
    override fun getCompleteTaskBetween(start: Long, end: Long): Single<List<Task>>

    @Query("SELECT (100 * a / b) as progress from (SELECT count(*) as a FROM Tasks WHERE (answer IS NOT NULL) AND (answered_at BETWEEN :start AND :end)) JOIN (SELECT count(*) as b FROM Tasks WHERE answered_at BETWEEN :start AND :end) ON 1 = 1")
    override fun getTaskProgressBetween(start: Long, end: Long): Maybe<Int>
}
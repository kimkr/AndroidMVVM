package com.dumi.svq_ver10.persistence.sources

import com.dumi.svq_ver10.persistence.model.Task
import io.reactivex.Maybe
import io.reactivex.Single

interface TaskDataSource {

    fun insertTask(task: Task)

    fun updateAnswer(id: String, answer: String): Long

    fun getAllIncompleteTasks(): Single<List<Task>>

    fun getCompleteTaskBetween(start: Long, end: Long): Single<List<Task>>

    fun getTaskProgressBetween(start: Long, end: Long): Maybe<Int>

    fun clearAll()
}
package com.dumi.svq_ver10.persistence.sources

import com.dumi.svq_ver10.persistence.model.Task
import io.reactivex.Single

interface TaskDataSource {

    fun getAllIncompleteTasks(): Single<List<Task>>
}
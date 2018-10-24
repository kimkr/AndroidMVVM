package com.dumi.svq_ver10.ui.main.taskincomplete

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.dumi.svq_ver10.persistence.model.Task
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import io.reactivex.disposables.Disposable

class IncompleteTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val tasks: ObservableList<Task> = ObservableArrayList()

    fun loadTasks(): Disposable = taskRepository.getAllIncompleteTasks()
            .subscribe { tasks ->
                this.tasks.clear()
                this.tasks.addAll(tasks)
            }

    companion object {
        const val TAG = "IncompleteTaskViewModel"
    }
}
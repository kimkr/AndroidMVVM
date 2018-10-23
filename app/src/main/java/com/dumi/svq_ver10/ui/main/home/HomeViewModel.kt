package com.dumi.svq_ver10.ui.main.home

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import io.reactivex.disposables.Disposable

class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val progress = ObservableInt(0)

    fun updateProgress(): Disposable = taskRepository.getWeeklyTaskProgress()
            .subscribe { progress -> this.progress.set(progress) }

    companion object {
        const val TAG = "HomeViewModel"
    }
}
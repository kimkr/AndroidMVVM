package com.dumi.svq_ver10.ui.main.taskcomplete

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import io.reactivex.disposables.Disposable
import java.util.*

class CompleteTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val year = ObservableInt(Calendar.getInstance().get(Calendar.YEAR))
    val month = ObservableInt(Calendar.getInstance().get(Calendar.MONTH) + 1)

    fun updateProgress(): Disposable = taskRepository.getWeeklyStat()
            .subscribe { progress ->
            }

    fun updateProgress(diff: Int): Disposable {
        var nextMonth = month.get() + diff
        var nextYear = year.get()
        if (nextMonth > 12) {
            nextMonth = 1
            nextYear += 1
        } else if (nextMonth < 1) {
            nextMonth = 12
            nextYear -= 1
        }
        year.set(nextYear)
        month.set(nextMonth)
        return updateProgress()
    }

    companion object {
        const val TAG = "CompleteTaskViewModel"
    }
}
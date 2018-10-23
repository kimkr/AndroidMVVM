package com.dumi.svq_ver10.ui.main.weeklystat

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import io.reactivex.disposables.Disposable

class WeeklyViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val mon = ObservableInt(0)
    val tue = ObservableInt(0)
    val wed = ObservableInt(0)
    val thu = ObservableInt(0)
    val fri = ObservableInt(0)
    val sat = ObservableInt(0)
    val sun = ObservableInt(0)

    fun updateProgress(): Disposable = taskRepository.getWeeklyStat()
            .subscribe { progress ->
                mon.set(progress[0])
                tue.set(progress[1])
                wed.set(progress[2])
                thu.set(progress[3])
                fri.set(progress[4])
                sat.set(progress[5])
                sun.set(progress[6])
            }

    companion object {
        const val TAG = "WeeklyViewModel"
    }
}
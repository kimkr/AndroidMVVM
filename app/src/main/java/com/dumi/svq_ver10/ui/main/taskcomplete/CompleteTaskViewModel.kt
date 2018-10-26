package com.dumi.svq_ver10.ui.main.taskcomplete

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.util.Log
import com.dumi.svq_ver10.persistence.model.Task
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.util.TimeUtil
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CompleteTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val year = ObservableInt(Calendar.getInstance().get(Calendar.YEAR))
    val month = ObservableInt(Calendar.getInstance().get(Calendar.MONTH) + 1)
    val tasks = ObservableArrayList<Any>()
    val empty = ObservableBoolean(false)

    fun loadMontlyCompleteTasks(): Disposable = taskRepository.getMonthlyCompleteTasks(year.get(), month.get())
            .subscribe { tasks ->
                var taskMap = tasks.fold(HashMap<String, ArrayList<Task>>()) { taskMap, task ->
                    var day = TimeUtil.formatToDay(task.answeredAt!!)
                    if (!taskMap.containsKey(day)) {
                        taskMap[day] = ArrayList()
                    }
                    taskMap[day]!!.add(task)
                    taskMap
                }
                var list = ArrayList<Any>()
                for (key in taskMap.keys) {
                    list.add(key)
                    list.addAll(taskMap[key]!!)
                }
                empty.set(list.isEmpty())
                this.tasks.clear()
                this.tasks.addAll(list)
                Log.d(TAG, "loadMontlyCompleteTasks ${this.tasks}")
            }

    fun loadMontlyCompleteTasks(diff: Int): Disposable {
        var nextMonth = TimeUtil.getNextMonth(year.get(), month.get(), diff)
        year.set(nextMonth.first)
        month.set(nextMonth.second)
        return loadMontlyCompleteTasks()
    }

    companion object {
        const val TAG = "CompleteTaskViewModel"
    }
}
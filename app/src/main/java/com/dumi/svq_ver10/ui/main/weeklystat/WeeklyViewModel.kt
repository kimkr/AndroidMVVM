package com.dumi.svq_ver10.ui.main.weeklystat

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableInt
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.util.TimeUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*


class WeeklyViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val mon = ObservableInt(0)
    val tue = ObservableInt(0)
    val wed = ObservableInt(0)
    val thu = ObservableInt(0)
    val fri = ObservableInt(0)
    val sat = ObservableInt(0)
    val sun = ObservableInt(0)

    fun updateProgress(): Disposable {
        val disposables = CompositeDisposable()
        var todayDayOfWeek = TimeUtil.getDayOfWeek()
        var daysOfWeek = listOf<Int>(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY)
        var limit = daysOfWeek.indexOf(todayDayOfWeek)
        for (dayOfWeek in daysOfWeek) {
            if (daysOfWeek.indexOf(dayOfWeek) > limit) {
                break
            }
            val timestamp = TimeUtil.getTimeOfDayOfWeek(dayOfWeek)
            val current = System.currentTimeMillis()
            if (timestamp <= current) {
                disposables.add(taskRepository.getDailyProgress(timestamp)
                        .subscribe { progress -> progressObservable(dayOfWeek).set(progress) }
                )
            }
        }
        return disposables
    }

    private fun progressObservable(dayOfWeek: Int): ObservableInt {
        return when (dayOfWeek) {
            Calendar.MONDAY -> mon
            Calendar.TUESDAY -> tue
            Calendar.WEDNESDAY -> wed
            Calendar.THURSDAY -> thu
            Calendar.FRIDAY -> fri
            Calendar.SATURDAY -> sat
            Calendar.SUNDAY -> sun
            else -> ObservableInt()
        }
    }

    companion object {
        const val TAG = "WeeklyViewModel"
    }
}
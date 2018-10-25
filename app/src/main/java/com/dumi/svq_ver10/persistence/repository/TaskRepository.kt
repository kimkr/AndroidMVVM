package com.dumi.svq_ver10.persistence.repository

import android.util.Log
import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.persistence.model.Task
import com.dumi.svq_ver10.persistence.sources.TaskDataSource
import com.dumi.svq_ver10.util.TimeUtil
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(@Local private val localTaskDataSource: TaskDataSource)
    : Repository {

    fun getWeeklyTaskProgress(): Maybe<Int> {
        var current = System.currentTimeMillis()
        var monday = TimeUtil.getTimeOfDayOfWeek(Calendar.MONDAY)
        Log.d("TaskRepository", "getWeeklyTaskProgress current : $current, monday: $monday")
        return localTaskDataSource.getTaskProgressBetween(monday, current)
                .onErrorReturn { 0 }
                .switchIfEmpty(Maybe.just(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDailyProgress(start: Long): Single<Int> {
        Log.d("TaskRepository", "getDailyProgress start: $start, end: ${start + TimeUtil.DAY_TO_MILLISECONDS}")
        return localTaskDataSource.getTaskProgressBetween(start, start + TimeUtil.DAY_TO_MILLISECONDS)
                .onErrorReturn { 0 }
                .defaultIfEmpty(0)
                .toSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMonthlyTaskProgress(year: Int, month: Int): Single<Int> {
        var start = TimeUtil.getTimeStampOf(year, month)
        var end = TimeUtil.getTimeStampOf(TimeUtil.getNextMonth(year, month, 1))
        return localTaskDataSource.getTaskProgressBetween(start, end)
                .onErrorReturn { 0 }
                .defaultIfEmpty(0)
                .toSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMonthlyCompleteTasks(year: Int, month: Int): Single<List<Task>> {
        var start = TimeUtil.getTimeStampOf(year, month)
        var end = TimeUtil.getTimeStampOf(TimeUtil.getNextMonth(year, month, 1))
        Log.d("TaskRepository", "getMonthlyCompleteTasks $start , $end")
        return localTaskDataSource.getCompleteTaskBetween(start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllIncompleteTasks() = localTaskDataSource.getAllIncompleteTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun insertTask(task: Task) = localTaskDataSource.insertTask(task)

    fun insertTask(id: String, userId: String, question: String) =
            insertTask(Task(id, null, userId, question, null, null, Date(), null))

    fun updateAsAnswered(id: String, answer: String): Single<Long> =
            Single.fromCallable { localTaskDataSource.updateAnswer(id, answer) }
}
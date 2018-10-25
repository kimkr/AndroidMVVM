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
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class TaskRepository @Inject constructor(@Local private val localTaskDataSource: TaskDataSource)
    : Repository {

    fun getWeeklyTaskProgress(): Maybe<Int> {
        return localTaskDataSource.getTaskProgressBetween(TimeUtil.getTimeStampOf(-7),
                System.currentTimeMillis())
                .switchIfEmpty(Maybe.just(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWeeklyStat(): Single<List<Int>> {
        return Single.just(Arrays.asList(11, 22, 33, 44, 55, 66, 77))
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMonthlyTaskProgress(year: Int, month: Int): Single<Int> {
        return Single.just((Math.random() * 100).roundToInt())
                .delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMonthlyCompleteTasks(pair: Pair<Int, Int>) =
            getMonthlyCompleteTasks(pair.first, pair.second)

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
}
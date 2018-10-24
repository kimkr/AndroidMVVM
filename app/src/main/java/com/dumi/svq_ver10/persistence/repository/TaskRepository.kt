package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.persistence.sources.TaskDataSource
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

    fun getWeeklyTaskProgress(): Single<Int> {
        return Single.just((Math.random() * 100).roundToInt())
                .delay(2000, TimeUnit.MILLISECONDS)
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

    fun getAllIncompleteTasks() = localTaskDataSource.getAllIncompleteTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
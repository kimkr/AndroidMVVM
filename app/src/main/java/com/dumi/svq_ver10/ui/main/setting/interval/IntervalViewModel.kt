package com.dumi.svq_ver10.ui.main.setting.interval

import android.arch.lifecycle.ViewModel
import com.dumi.svq_ver10.persistence.repository.SettingRepository

class IntervalViewModel(private val settingRepository: SettingRepository) : ViewModel() {

    fun getTimeSpan(): Pair<Int, Int> = settingRepository.getGpsTrackingTime()

    fun setTimeSpan(start: Int, end: Int) = settingRepository.setGpsTrackingTime(start, end)

    fun getInterval(): Int = settingRepository.getGpsInterval()

    fun setInterval(interval: Int) = settingRepository.setGpsInterval(interval)

    fun formatHour(hour: Int): String =
            when {
                hour == 12 -> "오후 $hour 시"
                hour > 12 -> "오후 " + (hour - 12) + " 시"
                else -> "오전 $hour 시"
            }

    companion object {
        var TAG = IntervalViewModel::class.java.simpleName
    }
}
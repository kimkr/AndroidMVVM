package com.dumi.svq_ver10.util

import android.text.format.DateFormat
import java.util.*

class TimeUtil {

    companion object {

        fun getTimeStampOf(pair: Pair<Int, Int>) = getTimeStampOf(pair.first, pair.second)

        fun getTimeStampOf(year: Int, month: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            return calendar.timeInMillis
        }

        fun getNextMonth(year: Int, month: Int, diff: Int): Pair<Int, Int> {
            var nextMonth = month + diff
            var nextYear = year
            if (nextMonth > 12) {
                nextMonth = 1
                nextYear += 1
            } else if (nextMonth < 1) {
                nextMonth = 12
                nextYear -= 1
            }
            return Pair(nextYear, nextMonth)
        }

        fun getTimeStampOf(dayDiff: Int): Long {
            return System.currentTimeMillis() + dayDiff * 24 * 60 * 60 * 1000
        }

        fun getDayOfWeek(): Int {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        fun getTimeOfDayOfWeek(dayOfWeek: Int): Long {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            return calendar.timeInMillis
        }

        fun getHour(): Int = (DateFormat.format("HH", Date()) as String).toInt()
        fun formatToDay(date: Date) = DateFormat.format("MM.dd", date) as String
        fun formatToTime(date: Date) = DateFormat.format("hh:mm aa", date) as String
        fun formatToMilli(date: Date) = DateFormat.format("yyyy-MM-dd hh:mm:ss", date) as String
        fun formatToGpsTime(date: Date) = DateFormat.format("yyyy-MM-dd HH:mm", date) as String

        const val DAY_TO_MILLISECONDS = 24 * 60 * 60 * 1000
    }
}
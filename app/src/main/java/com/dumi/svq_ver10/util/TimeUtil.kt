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

        fun formatToDay(date: Date) = DateFormat.format("MM.dd", date) as String
        fun formatToTime(date: Date) = DateFormat.format("hh:mm aa", date) as String
    }
}
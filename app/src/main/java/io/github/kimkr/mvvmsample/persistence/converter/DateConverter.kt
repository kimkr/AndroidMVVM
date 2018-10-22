package io.github.kimkr.mvvmsample.persistence.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(date: Date?): Long? {
        return date?.time
    }
}

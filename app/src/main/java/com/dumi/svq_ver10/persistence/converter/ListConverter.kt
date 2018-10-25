package com.dumi.svq_ver10.persistence.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class ListConverter {

    @TypeConverter
    fun toList(list: String?): List<String> {
        return if (list == null) ArrayList() else list.split(SEPARATOR)
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        return list?.joinToString(SEPARATOR)
    }

    companion object {
        const val SEPARATOR = ","
    }
}

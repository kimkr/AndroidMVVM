package com.dumi.svq_ver10.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.dumi.svq_ver10.persistence.model.Gender

class GenderConverter {

    @TypeConverter
    fun toGender(value: String?): Gender? {
        return if (value == null) null else Gender.from(value)
    }

    @TypeConverter
    fun toCode(gender: Gender?): String? {
        return gender?.code
    }
}

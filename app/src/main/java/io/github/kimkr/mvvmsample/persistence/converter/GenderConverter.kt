package io.github.kimkr.mvvmsample.persistence.converter

import android.arch.persistence.room.TypeConverter
import io.github.kimkr.mvvmsample.persistence.model.Gender

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

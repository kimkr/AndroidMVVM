package com.dumi.svq_ver10.persistence.converter

import android.arch.persistence.room.TypeConverter
import com.dumi.svq_ver10.persistence.model.QuestionType

class QuestionTypeConverter {

    @TypeConverter
    fun toType(value: String?): QuestionType? {
        return if (value == null) null else QuestionType.from(value)
    }

    @TypeConverter
    fun toString(type: QuestionType?): String? {
        return type?.code
    }
}

package com.dumi.svq_ver10.persistence.model

enum class QuestionType(val code: String) {
    RADIO("radio"),
    SLIDE("slide"),
    LOCATION("location"),
    CHECKBOX("checkbox"),
    TEXT("simple text"),
    TREE("tree");

    companion object {
        fun from(code: String): QuestionType? {
            for (type in QuestionType.values()) {
                if (type.code == code) {
                    return type
                }
            }
            return null
        }
    }
}
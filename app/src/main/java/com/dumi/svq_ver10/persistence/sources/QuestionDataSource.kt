package com.dumi.svq_ver10.persistence.sources

import com.dumi.svq_ver10.persistence.model.Question
import io.reactivex.Maybe

interface QuestionDataSource {

    fun getQuestionById(id: String): Maybe<Question>

    fun getQuestionsByTask(id: String): Maybe<List<Question>>

    fun insertQuestion(question: Question)

    fun removeQuestion(id: String)

    fun removeAll()

    fun updateAnswer(id: String, answer: String): Long

    fun countNotAnsweredOf(task: String): Int
}
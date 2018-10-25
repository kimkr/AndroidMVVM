package com.dumi.svq_ver10.persistence.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.persistence.sources.QuestionDataSource
import io.reactivex.Maybe

@Dao
interface QuestionDao : QuestionDataSource {

    @Query("SELECT * FROM Questions WHERE id = :id")
    override fun getQuestionById(id: String): Maybe<Question>

    @Query("SELECT * FROM Questions WHERE task = :task")
    override fun getQuestionsByTask(task: String): Maybe<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertQuestion(question: Question)

    @Query("DELETE FROM Questions WHERE id = :id")
    override fun removeQuestion(id: String)

    @Query("DELETE FROM Questions")
    override fun removeAll()

    @Query("UPDATE Questions SET answer = :answer, updated = CURRENT_TIMESTAMP WHERE id = :id")
    override fun updateAnswer(id: String, answer: String): Long

    @Query("SELECT COUNT(*) FROM Questions WHERE task = :task AND answer IS NULL")
    override fun countNotAnsweredOf(task: String): Int
}
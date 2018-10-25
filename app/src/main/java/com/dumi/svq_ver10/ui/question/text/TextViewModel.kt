package com.dumi.svq_ver10.ui.question.text

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import com.dumi.svq_ver10.persistence.remote.QuestionService
import com.dumi.svq_ver10.persistence.repository.QuestionRepository
import io.reactivex.Maybe
import io.reactivex.disposables.Disposable

class TextViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    val question = ObservableField<String>()
    lateinit var task: String

    fun loadQuestions(taskId: String): Disposable =
            questionRepository.getQuestionsByTask(taskId)
                    .subscribe { questions ->
                        Log.d(TAG, "questions: $questions")
                        if (!questions.isEmpty()) {
                            this.question.set(questions[0].content)
                            this.task = taskId
                        }
                    }

    fun sendAnswer(answer: String): Maybe<Boolean> =
            questionRepository.sendAnswer(answer, task, QuestionService.Type.TEXT)

    companion object {
        const val TAG = "TextViewModel"
    }
}
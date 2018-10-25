package com.dumi.svq_ver10.ui.question.text

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.persistence.repository.QuestionRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

class TextViewModel(private val questionRepository: QuestionRepository,
                    private val taskRepository: TaskRepository) : ViewModel() {

    val question = ObservableField<String>()
    lateinit var obj: Question

    fun loadQuestion(questionId: String): Disposable =
            questionRepository.getQuestionById(questionId)
                    .subscribe { question ->
                        Log.d(TAG, "question: $question")
                        this.obj = question
                        this.question.set(question.content)
                    }

    fun sendAnswer(answer: String): Maybe<Boolean> =
            questionRepository.updateAnswer(obj.code, answer)
                    .zipWith(
                            questionRepository.countNotAnsweredOf(obj.task)
                                    .flatMap { count ->
                                        if (count == 0) taskRepository.updateAsAnswered(obj.task, answer)
                                        else Single.just(1)
                                    }
                    )
                    .flatMapMaybe { ret ->
                        Log.d(TAG, "sendAnswer : $ret")
                        if (ret.first > 0 && (ret.second as Long) > 0)
                            questionRepository.sendAnswer(answer, obj.task, obj.type)
                        else
                            Maybe.just(false)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    companion object {
        const val TAG = "TextViewModel"
    }
}
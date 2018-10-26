package com.dumi.svq_ver10.ui.question

import android.util.Log
import com.dumi.svq_ver10.persistence.model.QuestionType
import com.dumi.svq_ver10.persistence.repository.QuestionRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.ui.question.text.TextViewModel
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

class QuestionViewModel(var taskRepository: TaskRepository, var questionRepository: QuestionRepository) {

    fun sendAnswer(taskId: String, questionId: String, type: QuestionType, answer: String): Maybe<Boolean> =
            questionRepository.updateAnswer(questionId, answer)
                    .zipWith(
                            questionRepository.countNotAnsweredOf(taskId)
                                    .flatMap { count ->
                                        if (count == 0) taskRepository.updateAsAnswered(taskId, answer)
                                        else Single.just(1)
                                    }
                    )
                    .flatMapMaybe { ret ->
                        Log.d(TextViewModel.TAG, "sendAnswer : $ret")
                        if (ret.first > 0 && (ret.second as Long) > 0)
                            questionRepository.sendAnswer(answer, taskId, type)
                        else
                            Maybe.just(false)
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}
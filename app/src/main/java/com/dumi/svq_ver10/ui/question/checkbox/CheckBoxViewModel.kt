package com.dumi.svq_ver10.ui.question.checkbox

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.util.Log
import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.persistence.model.QuestionType
import com.dumi.svq_ver10.persistence.repository.QuestionRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers


class CheckBoxViewModel(private val questionRepository: QuestionRepository,
                        private val taskRepository: TaskRepository) : ViewModel() {

    val question = ObservableField<String>()
    val options: ObservableList<String> = ObservableArrayList()
    val choiceSingle = ObservableBoolean(true)

    lateinit var obj: Question

    fun loadQuestion(questionId: String): Disposable =
            questionRepository.getQuestionById(questionId)
                    .subscribe { question ->
                        Log.d(TAG, "question: $question")
                        this.obj = question
                        this.choiceSingle.set(question.type != QuestionType.CHECKBOX)
                        this.question.set(question.content)
                        this.options.addAll(question.values)
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

    fun formatAnswer(selectedPositions: Set<Int>): String {
        var values = selectedPositions.fold(ArrayList<String>())
        { list, idx ->
            list.add(options[idx])
            list
        }
        return values.joinToString(",")
    }

    companion object {
        const val TAG = "CheckBoxViewModel"
    }
}
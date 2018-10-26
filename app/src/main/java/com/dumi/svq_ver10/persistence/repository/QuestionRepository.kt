package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.persistence.model.QuestionType
import com.dumi.svq_ver10.persistence.remote.QuestionService
import com.dumi.svq_ver10.persistence.sources.QuestionDataSource
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(@Local private val localQuestionDataSource: QuestionDataSource,
                                             private val questionService: QuestionService,
                                             private val authRepository: AuthRepository)
    : Repository {
    fun getQuestionById(id: String) = localQuestionDataSource.getQuestionById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getQuestionsByTask(task: String) = localQuestionDataSource.getQuestionsByTask(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun insertQuestion(question: Question) = localQuestionDataSource.insertQuestion(question)

    fun removeQuestion(id: String) = localQuestionDataSource.removeQuestion(id)

    fun removeAll(): Completable = Completable.fromAction { localQuestionDataSource.removeAll() }

    fun loadQuestionList(userId: String) {
        questionService.getList(userId)
                .subscribe { questions ->
                    for (question in questions) {
                        localQuestionDataSource.insertQuestion(question)
                    }
                }
    }

    fun saveQuestion(jsonStr: String) {
        questionService.parseQuestion(jsonStr)
                .subscribe { questions ->
                    for (question in questions) {
                        localQuestionDataSource.insertQuestion(question)
                    }
                }
    }

    fun updateAnswer(id: String, answer: String): Single<Long> =
            Single.fromCallable { localQuestionDataSource.updateAnswer(id, answer) }

    fun sendAnswer(answer: String, task: String, type: QuestionType): Maybe<Boolean> {
        var userId = authRepository.getUserId()
        var token = authRepository.getToken()
        return questionService.answerQuestion(userId, token, task, answer, type)
    }

    fun countNotAnsweredOf(task: String): Single<Int> =
            Single.fromCallable { localQuestionDataSource.countNotAnsweredOf(task) }
}
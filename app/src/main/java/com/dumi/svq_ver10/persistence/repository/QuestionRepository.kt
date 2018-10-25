package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.di.qualifier.Local
import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.persistence.remote.QuestionService
import com.dumi.svq_ver10.persistence.sources.QuestionDataSource
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(@Local private val localQuestionDataSource: QuestionDataSource,
                                             private val questionService: QuestionService,
                                             private val authRepository: AuthRepository)
    : Repository, QuestionDataSource {


    override fun getQuestionById(id: String) = localQuestionDataSource.getQuestionById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getQuestionsByTask(task: String) = localQuestionDataSource.getQuestionsByTask(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun insertQuestion(question: Question) = localQuestionDataSource.insertQuestion(question)

    override fun removeQuestion(id: String) = localQuestionDataSource.removeQuestion(id)

    override fun removeAll() = localQuestionDataSource.removeAll()

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

    fun sendAnswer(answer: String, task: String, type: QuestionService.Type): Maybe<Boolean> {
        var userId = authRepository.getUserId()
        var token = authRepository.getToken()
        return questionService.answerQuestion(userId, token, task, answer, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
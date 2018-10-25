package com.dumi.svq_ver10.persistence.remote

import android.util.Log
import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.persistence.model.QuestionType
import com.dumi.svq_ver10.persistence.model.QuestionType.*
import com.dumi.svq_ver10.util.TimeUtil
import com.google.gson.Gson
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionService @Inject constructor(private val questionAPI: QuestionAPI) {

    fun getList(id: String): Maybe<List<Question>> {
        return questionAPI.getList(id)
                .map { response ->
                    transform(response)
                }
    }

    fun parseQuestion(str: String): Single<List<Question>> {
        var gson = Gson()
        return Single.just(str)
                .map { responseStr -> gson.fromJson(responseStr, ListResponse::class.java) }
                .map { response -> transform(response) }
    }

    fun answerQuestion(userId: String, token: String, taskId: String,
                       answer: String, type: QuestionType): Maybe<Boolean> {
        var date = TimeUtil.formatToMilli(Date())
        return questionAPI.answerQuestion(userId, token, taskId, answer, type.code, date)
                .map { res ->
                    Log.d("QuestionService", "res : $res")
                    res.msg == "0"
                }
                .subscribeOn(Schedulers.io())
    }

    private fun transform(response: ListResponse): List<Question> {
        var ret = ArrayList<Question>()
        var tasknum = if (response.listNum == null) 1 else response.listNum.toInt()
        var msg = response.ctk_queMsg.split(RESPONSE_DELIM)
        var settingVal =
                if (response.ctk_queSettingVal == null) ArrayList()
                else response.ctk_queSettingVal.split(RESPONSE_DELIM)
        var taskname = response.ctk_taskName.split(RESPONSE_DELIM)
        var method = response.ctk_queMethod.split(RESPONSE_DELIM)
        var num = ""
        var treeId = "0"
        var left = ""
        var right = ""
        for (i in 0 until tasknum) {
            val settings = settingVal[i].split(",")
            val type = QuestionType.from(method[i])
            var values = ArrayList<String>()
            when (type) {
                RADIO, CHECKBOX -> {
                    num = settings[0]
                    for (j in 1..num.toInt()) {
                        values.add(settings[j])
                    }
                }
                SLIDE -> {
                    left = settings[0]
                    right = settings[1]
                    if (settings.size > 2) {
                        var size = settings[2].toInt()
                        num = size.toString()
                        for (i in 3..(2 + size))
                            if (i < settings.size) {
                                values.add(settings[i])
                            }
                    }
                }
                TREE -> {
                    treeId = settings[0]
                    num = settings[1]
                    for (j in 2..num.toInt() + 1) {
                        values.add(settings[j])
                    }
                }
            }
            val question = Question(taskname[i] + i, treeId, type!!, "0", msg[i],
                    num, left, right, values, taskname[i], null, Date(), null)
            ret.add(question)
        }
        return ret
    }

    interface QuestionAPI {
        @POST("getList.do")
        fun getList(@Query("cuser_id") userId: String): Maybe<ListResponse>

        @POST("AndroidRequestTask1.do")
        fun answerQuestion(
                @Query("ctk_answerId") userId: String,
                @Query("ctk_token") token: String,
                @Query("ctk_taskName") taskId: String,
                @Query("ctk_answer") answer: String,
                @Query("ctk_queMethod") type: String,
                @Query("ctk_answerDate") date: String): Maybe<AnswerResponse>
    }

    data class ListResponse(val listNum: String?,
                            val ctk_queId: String,
                            val ctk_taskName: String,
                            val ctk_queMsg: String,
                            val ctk_queSettingVal: String?,
                            val ctk_queMethod: String)

    data class AnswerResponse(val msg: String)

    companion object {
        var RESPONSE_DELIM = ";"
    }
}


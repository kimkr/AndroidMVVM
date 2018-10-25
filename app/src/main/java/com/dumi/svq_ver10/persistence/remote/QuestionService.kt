package com.dumi.svq_ver10.persistence.remote

import com.dumi.svq_ver10.persistence.model.Question
import com.dumi.svq_ver10.util.TimeUtil
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
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

    fun answerQuestion(userId: String, token: String, taskId: String,
                       answer: String, type: Type): Maybe<Boolean> {
        var date = TimeUtil.formatToMilli(Date())
        return questionAPI.answerQuestion(userId, token, taskId, answer,
                type.code, date)
                .map { response -> response.msg == "0" }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun transform(response: ListResponse): List<Question> {
        var ret = ArrayList<Question>()
        var tasknum = response.listNum.toInt()
        var msg = response.ctk_queMsg.split(RESPONSE_DELIM)
        var settingVal = response.ctk_queSettingVal.split(RESPONSE_DELIM)
        var taskname = response.ctk_taskName.split(RESPONSE_DELIM)
        var method = response.ctk_queMethod.split(RESPONSE_DELIM)
        var num = ""
        var treeId = "0"
        for (i in 0 until tasknum) {
            val settings = settingVal[i].split(",")
            val type = Type.from(method[i])
            var values = ArrayList<String>()
            when (type) {
                Type.RADIO -> {
                    num = settings[0]
                    for (j in 1..num.toInt()) {
                        values.add(settings[j])
                    }
                }
                Type.SLIDE -> {
                    num = settings[1]
                    values.add(settings[3])
                    if (settings[2].toInt() >= 2)
                        values.add(settings[2 + settings[2].toInt()])
                }
                Type.CHECKBOX -> {
                    num = settings[0]
                    for (j in 1..num.toInt()) {
                        values.add(settings[j])
                    }
                }
                Type.TREE -> {
                    treeId = settings[0]
                    num = settings[1]
                    for (j in 2..num.toInt() + 1) {
                        values.add(settings[j])
                    }
                }
            }
            val question = Question(taskname[i], treeId, method[i], "0", msg[i], num, "", "", values)
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

    data class ListResponse(val listNum: String,
                            val ctk_taskName: String,
                            val ctk_queMsg: String,
                            val ctk_queSettingVal: String,
                            val ctk_queMethod: String)

    data class AnswerResponse(val msg: String)

    enum class Type(val code: String) {
        RADIO("radio"),
        SLIDE("slide"),
        CHECKBOX("checkbox"),
        TREE("tree");

        companion object {
            fun from(code: String): Type? {
                for (type in Type.values()) {
                    if (type.code == code) {
                        return type
                    }
                }
                return null
            }
        }
    }

    companion object {
        var RESPONSE_DELIM = ";"
    }
}


package com.dumi.svq_ver10.persistence.remote

import com.dumi.svq_ver10.persistence.model.Gender
import com.dumi.svq_ver10.persistence.model.User
import com.dumi.svq_ver10.persistence.repository.AuthRepository
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskService @Inject constructor(private val userAPI: TaskAPI,
                                      private val authRepository: AuthRepository) {
    fun getUserById(id: String): Maybe<User> {
        val token = authRepository.getToken()
        return userAPI.getUserById(id, token)
                .flatMap { res ->
                    if (res.msg != "1")
                        Maybe.empty()
                    else
                        Maybe.just(transform(id, res.array))
                }
    }

    private fun transform(id: String, array: Array<String>): User {
        val name = array[0]
        val email = array[1]
        val phone = array[5]
        val date = array[2]
        val gender = Gender.from(array[3]) ?: Gender.MALE
        val manager = array[4]
        val autoLogin = true
        return User(id, name, email, phone, date, gender, manager, autoLogin)
    }

    interface TaskAPI {
        @POST("getAndroidLogin.do")
        fun getUserById(@Query("cuser_id") userId: String,
                        @Query("cuser_token") token: String): Maybe<LoginResponse>
    }

    data class LoginResponse(val array: Array<String>, val msg: String)
}


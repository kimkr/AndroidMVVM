package com.dumi.svq_ver10.persistence.cache

import com.dumi.svq_ver10.persistence.model.Gender
import com.dumi.svq_ver10.persistence.model.User
import com.dumi.svq_ver10.persistence.repository.AuthRepository
import com.dumi.svq_ver10.persistence.sources.UserDataSource
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService @Inject constructor(private val userAPI: UserAPI,
                                      private val authRepository: AuthRepository) : UserDataSource {

    private val testToken = "cmQR4Bbg5qU:APA91bHkQNz-oXGmdhFwN4t4MflmjTP7xohrhoXSd1Tv6jl9U4dEDtnJVl375KwmITpdgpuo2MbClZ6JckckldBZKXrFqqB6cuwJWx2sz3M6x_XUM__bAWlBea9iwQPmJcrUyozRPJm9"

    override fun getUser() = Maybe.empty<User>()

    override fun getUserById(id: String): Maybe<User> {
        val token = authRepository.getToken() ?: testToken
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

    override fun insertUser(user: User) {
    }

    override fun deleteAllUsers() {
    }

    interface UserAPI {
        @POST("getAndroidLogin.do")
        fun getUserById(@Query("cuser_id") userId: String,
                        @Query("cuser_token") token: String): Maybe<LoginResponse>
    }

    data class LoginResponse(val array: Array<String>, val msg: String)
}


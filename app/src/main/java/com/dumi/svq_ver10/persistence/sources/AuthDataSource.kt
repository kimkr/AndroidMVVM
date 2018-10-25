package com.dumi.svq_ver10.persistence.sources

interface AuthDataSource {

    fun getToken(): String?

    fun setToken(token: String)

    fun deleteToken()

    fun setLogin()

    fun setLogout()

    fun isLogin(): Boolean
}
package io.github.kimkr.mvvmsample.persistence.sources

interface AuthDataSource {

    fun getToken(): String?

    fun setToken(token: String)

    fun deleteToken()
}
package io.github.kimkr.mvvmsample.persistence.repository

import io.github.kimkr.mvvmsample.persistence.preferences.AuthPreferences
import io.github.kimkr.mvvmsample.persistence.sources.AuthDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val localDataSource: AuthPreferences)
    : AuthDataSource, Repository {

    override fun getToken(): String? {
        return localDataSource.getToken()
    }

    override fun setToken(token: String) {
       localDataSource.setToken(token)
    }

    override fun deleteToken() {
       localDataSource.deleteToken()
    }
}
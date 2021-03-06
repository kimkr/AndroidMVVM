package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.persistence.preferences.AuthPreferences
import com.dumi.svq_ver10.persistence.sources.AuthDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val localDataSource: AuthPreferences)
    : AuthDataSource, Repository {

    override fun getToken() = localDataSource.getToken()

    override fun setToken(token: String) =
            localDataSource.setToken(token)

    override fun deleteToken() = localDataSource.deleteToken()

    override fun isLogin() = localDataSource.isLogin()

    override fun setLogin(userId: String) = localDataSource.setLogin(userId)

    override fun getUserId() = localDataSource.getUserId()

    override fun setLogout() = localDataSource.setLogout()
}
package com.dumi.svq_ver10.persistence.preferences

import com.dumi.svq_ver10.persistence.sources.AuthDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreferences @Inject constructor(private val manager: PreferenceManager) : AuthDataSource {

    override fun getToken() = manager.pref.getString(TOKEN_KEY, null)

    override fun setToken(token: String) = manager.put(TOKEN_KEY, token)

    override fun deleteToken() = manager.delete(TOKEN_KEY)

    override fun setLogin(userId: String) = manager.put(LOGIN_FLAG, userId)

    override fun setLogout() = manager.put(LOGIN_FLAG, "")

    override fun isLogin() = !manager.pref.getString(LOGIN_FLAG, "").isNullOrEmpty()

    override fun getUserId() = manager.pref.getString(LOGIN_FLAG, "")

    companion object {
        val TOKEN_KEY = "AUTH_TOKEN"
        val LOGIN_FLAG = "LOGIN_FLAG"
    }
}
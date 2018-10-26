package com.dumi.svq_ver10.persistence.preferences

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(val app: Application) {

    var pref: SharedPreferences

    init {
        pref = app.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun put(key: String, value: Int) {
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun put(key: String, value: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun put(key: String, value: Float) {
        val editor = pref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun put(key: String, value: Long) {
        val editor = pref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun delete(key: String) {
        val editor = pref.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        var PREFERENCE_NAME = "SVQ_PREF"
    }
}
package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.persistence.preferences.SettingPreferences
import com.dumi.svq_ver10.persistence.sources.SettingDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(private val settingPreferences: SettingPreferences)
    : Repository, SettingDataSource {

    override fun isGpsAllowed() =
            settingPreferences.isGpsAllowed()

    override fun setGpsAllowed(allowed: Boolean) = settingPreferences.setGpsAllowed(allowed)

    override fun getGpsTrakingTime(): Pair<Int, Int> = settingPreferences.getGpsTrakingTime()

    override fun setGpsTrakingTime(start: Int, end: Int) = settingPreferences.setGpsTrakingTime(start, end)
}
package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.persistence.preferences.SettingPreferences
import com.dumi.svq_ver10.persistence.sources.SettingDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(private val settingPreferences: SettingPreferences)
    : Repository, SettingDataSource {
    override fun setGpsInterval(time: Int) = settingPreferences.setGpsInterval(time)

    override fun getGpsInterval(): Int = settingPreferences.getGpsInterval()

    override fun isPushAllowed(): Boolean = settingPreferences.isPushAllowed()

    override fun setPushAllowed(allowed: Boolean) = settingPreferences.setPushAllowed(allowed)

    override fun setLocationAllowed(allowed: Boolean) = settingPreferences.setLocationAllowed(allowed)

    override fun isLocationAllowed(): Boolean = settingPreferences.isLocationAllowed()

    override fun isGpsAllowed() =
            settingPreferences.isGpsAllowed()

    override fun setGpsAllowed(allowed: Boolean) = settingPreferences.setGpsAllowed(allowed)

    override fun getGpsTrackingTime(): Pair<Int, Int> = settingPreferences.getGpsTrackingTime()

    override fun setGpsTrackingTime(start: Int, end: Int) = settingPreferences.setGpsTrackingTime(start, end)

    fun clearAll() = settingPreferences.clearAll()
}
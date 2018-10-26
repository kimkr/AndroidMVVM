package com.dumi.svq_ver10.persistence.preferences

import com.dumi.svq_ver10.persistence.sources.SettingDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingPreferences @Inject constructor(private val manager: PreferenceManager) : SettingDataSource {

    override fun isGpsAllowed() = manager.pref.getBoolean(GPS_ALLOWDED, false)

    override fun setGpsAllowed(allowed: Boolean) = manager.put(GPS_ALLOWDED, allowed)

    override fun getGpsTrakingTime() = Pair(manager.pref.getInt(GPS_TRACKING_START_TIME, 0),
            manager.pref.getInt(GPS_TRACKING_END_TIME, 0))

    override fun setGpsTrakingTime(start: Int, end: Int) {
        manager.put(GPS_TRACKING_START_TIME, start)
        manager.put(GPS_TRACKING_END_TIME, end)
    }

    companion object {
        const val GPS_ALLOWDED = "GPS_ALLOWDED"
        const val GPS_TRACKING_START_TIME = "GPS_TRACKING_START_TIME"
        const val GPS_TRACKING_END_TIME = "GPS_TRACKING_END_TIME"
    }
}
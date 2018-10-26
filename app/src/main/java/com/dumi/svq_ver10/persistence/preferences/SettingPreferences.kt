package com.dumi.svq_ver10.persistence.preferences

import com.dumi.svq_ver10.persistence.sources.SettingDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingPreferences @Inject constructor(private val manager: PreferenceManager) : SettingDataSource {
    override fun setGpsInterval(time: Int) = manager.put(GPS_INTERVAL, time)

    override fun getGpsInterval(): Int = manager.pref.getInt(GPS_INTERVAL, 10)

    override fun isPushAllowed(): Boolean = manager.pref.getBoolean(PUSH_ALLOWED, false)

    override fun setPushAllowed(allowed: Boolean) = manager.put(PUSH_ALLOWED, allowed)

    override fun setLocationAllowed(allowed: Boolean) = manager.put(LOCATION_ALLOWED, allowed)

    override fun isLocationAllowed(): Boolean = manager.pref.getBoolean(LOCATION_ALLOWED, false)

    override fun isGpsAllowed() = manager.pref.getBoolean(GPS_ALLOWED, false)

    override fun setGpsAllowed(allowed: Boolean) = manager.put(GPS_ALLOWED, allowed)

    override fun getGpsTrackingTime() = Pair(manager.pref.getInt(GPS_TRACKING_START_TIME, 0),
            manager.pref.getInt(GPS_TRACKING_END_TIME, 0))

    override fun setGpsTrackingTime(start: Int, end: Int) {
        manager.put(GPS_TRACKING_START_TIME, start)
        manager.put(GPS_TRACKING_END_TIME, end)
    }

    fun clearAll() {
        manager.delete(PUSH_ALLOWED)
        manager.delete(GPS_ALLOWED)
        manager.delete(GPS_INTERVAL)
        manager.delete(GPS_TRACKING_START_TIME)
        manager.delete(GPS_TRACKING_END_TIME)
        manager.delete(LOCATION_ALLOWED)
    }

    companion object {
        const val PUSH_ALLOWED = "PUSH_ALLOWED"
        const val GPS_ALLOWED = "GPS_ALLOWED"
        const val GPS_INTERVAL = "GPS_INTERVAL"
        const val GPS_TRACKING_START_TIME = "GPS_TRACKING_START_TIME"
        const val GPS_TRACKING_END_TIME = "GPS_TRACKING_END_TIME"
        const val LOCATION_ALLOWED = "LOCATION_ALLOWED"
    }
}
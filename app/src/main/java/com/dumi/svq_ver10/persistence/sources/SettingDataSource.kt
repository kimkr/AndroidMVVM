package com.dumi.svq_ver10.persistence.sources

interface SettingDataSource {

    fun setGpsInterval(time: Int)

    fun getGpsInterval(): Int

    fun isPushAllowed(): Boolean

    fun setPushAllowed(allowed: Boolean)

    fun isGpsAllowed(): Boolean

    fun setGpsAllowed(allowed: Boolean)

    fun isLocationAllowed(): Boolean

    fun setLocationAllowed(allowed: Boolean)

    fun getGpsTrackingTime(): Pair<Int, Int>

    fun setGpsTrackingTime(start: Int, end: Int)
}
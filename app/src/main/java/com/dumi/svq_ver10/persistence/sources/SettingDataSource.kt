package com.dumi.svq_ver10.persistence.sources

interface SettingDataSource {

    fun isGpsAllowed(): Boolean

    fun setGpsAllowed(allowed: Boolean)

    fun getGpsTrakingTime(): Pair<Int, Int>

    fun setGpsTrakingTime(start: Int, end: Int)
}
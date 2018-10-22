package com.dumi.svq_ver10.persistence.preferences

import com.dumi.svq_ver10.persistence.sources.LocationDataSource
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPreferences @Inject constructor(private val manager: PreferenceManager) : LocationDataSource {
    override fun getLocation(): LatLng? {
        val locationVal = manager.pref.getString(LOCATION_KEY, null)
        if (!locationVal.isNullOrEmpty()) {
            val splitted = locationVal.split("-")
            return LatLng(splitted[0].toDouble(), splitted[1].toDouble())
        }
        return null
    }

    override fun updateLocation(lat: Double, lng: Double): Completable {
        return Completable.fromAction { manager.put(LOCATION_KEY, "$lat-$lng") }
    }

    companion object {
        val LOCATION_KEY = "LOCATION_KEY"
    }
}
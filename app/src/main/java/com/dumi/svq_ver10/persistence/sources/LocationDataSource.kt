package com.dumi.svq_ver10.persistence.sources

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable

interface LocationDataSource {

    fun updateLocation(lat: Double, lng: Double): Completable

    fun getLocation(): LatLng?

    fun updateAddress(address: String): Completable

    fun getAddress(): String?

    fun updateGps(latitude: Double, longitude: Double): Completable

    fun clearAll()
}
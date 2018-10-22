package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.persistence.cache.LocationService
import com.dumi.svq_ver10.persistence.preferences.LocationPreferences
import com.dumi.svq_ver10.persistence.sources.LocationDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val localDataSource: LocationPreferences,
                                             private val remoteDataSource: LocationService)
    : LocationDataSource, Repository {
    override fun getLocation() = localDataSource.getLocation()

    override fun updateLocation(lat: Double, lng: Double): Completable {
        return remoteDataSource.updateLocation(lat, lng)
                .andThen(localDataSource.updateLocation(lat, lng))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
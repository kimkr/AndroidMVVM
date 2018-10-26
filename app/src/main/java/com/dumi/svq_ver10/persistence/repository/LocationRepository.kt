package com.dumi.svq_ver10.persistence.repository

import com.dumi.svq_ver10.persistence.preferences.LocationPreferences
import com.dumi.svq_ver10.persistence.remote.LocationService
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
    override fun updateGps(latitude: Double, longitude: Double): Completable =
            remoteDataSource.updateGps(latitude, longitude)
                    .subscribeOn(Schedulers.io())

    override fun getAddress() = localDataSource.getAddress()

    override fun updateAddress(address: String) = localDataSource.updateAddress(address)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getLocation() = localDataSource.getLocation()

    override fun updateLocation(lat: Double, lng: Double): Completable {
        return remoteDataSource.updateLocation(lat, lng)
                .andThen(localDataSource.updateLocation(lat, lng))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
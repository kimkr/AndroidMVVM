package com.dumi.svq_ver10.persistence.remote

import com.dumi.svq_ver10.persistence.repository.AuthRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.persistence.sources.LocationDataSource
import com.dumi.svq_ver10.util.TimeUtil
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationService @Inject constructor(private val locationAPI: LocationAPI,
                                          private val authRepository: AuthRepository,
                                          private val userRepository: UserRepository) : LocationDataSource {
    override fun clearAll() {
    }

    override fun updateGps(latitude: Double, longitude: Double): Completable {
        var userId = authRepository.getUserId()
        return locationAPI.updateGps(userId, TimeUtil.formatToGpsTime(Date()), "$latitude-$longitude")
                .to { res -> Completable.fromMaybe(res) }
    }

    override fun getAddress(): String? {
        return null
    }

    override fun updateAddress(address: String): Completable {
        return Completable.fromCallable { }
    }

    override fun getLocation(): LatLng? {
        return null
    }

    override fun updateLocation(lat: Double, lng: Double): Completable {
        return userRepository.getUser()
                .flatMap { user ->
                    locationAPI.updateLocation(user.id, transform(lat, lng))
                }.to { it ->
                    Completable.fromMaybe(it)
                }
    }

    private fun transform(lat: Double, lng: Double) = "$lat-$lng"

    interface LocationAPI {
        @POST("getUserHome.do")
        fun updateLocation(@Query("cuser_id") userId: String,
                           @Query("cuser_gps") gps: String): Maybe<Response>

        @POST("ClientGps.do")
        fun updateGps(@Query("gps_userId") userId: String,
                      @Query("gps_time") time: String,
                      @Query("gps_data") data: String): Maybe<Response>
    }

    data class Response(val msg: String)
}


package com.dumi.svq_ver10.persistence.cache

import com.dumi.svq_ver10.persistence.repository.AuthRepository
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.persistence.sources.LocationDataSource
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Maybe
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationService @Inject constructor(private val locationAPI: LocationAPI,
                                          private val authRepository: AuthRepository,
                                          private val userRepository: UserRepository) : LocationDataSource {
    override fun getLocation(): LatLng? {
        return null
    }

    private val testToken = "cmQR4Bbg5qU:APA91bHkQNz-oXGmdhFwN4t4MflmjTP7xohrhoXSd1Tv6jl9U4dEDtnJVl375KwmITpdgpuo2MbClZ6JckckldBZKXrFqqB6cuwJWx2sz3M6x_XUM__bAWlBea9iwQPmJcrUyozRPJm9"

    override fun updateLocation(lat: Double, lng: Double): Completable {
        val token = authRepository.getToken() ?: testToken
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
    }

    data class Response(val msg: String)
}


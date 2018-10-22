package com.dumi.svq_ver10.util.location

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class GoogleMapService @Inject constructor(private val placesAPI: PlacesAPI,
                                           private val geoCoderAPI: GeoCoderAPI,
                                           @Named("google_maps_key") private val key: String) {

    fun searchNearbyPlaces(lat: Double, lng: Double, radius: Int,
                           keyword: String, lang: String = "ko"): Maybe<List<Result>> {
        return placesAPI.searchNearbyPlaces("$lat,$lng",
                radius,
                keyword,
                key, lang)
                .map { response ->
                    if (response.status == STATUS_OK) {
                        response.results.fold(ArrayList<Result>())
                        { results, place ->
                            results.add(Result(place.name,
                                    place.geometry.location.lat, place.geometry.location.lng))
                            results
                        }
                    } else {
                        ArrayList<Result>()
                    }
                }
    }

    fun getAddressOf(lat: Double, lng: Double, lang: String = "ko"): Maybe<List<Result>> {
        return geoCoderAPI.getAddressOf("$lat,$lng", false, key, lang)
                .map { response ->
                    if (response.status == STATUS_OK) {
                        response.results.fold(ArrayList<Result>())
                        { results, place ->
                            results.add(Result(place.formatted_address,
                                    place.geometry.location.lat, place.geometry.location.lng))
                            results
                        }
                    } else {
                        ArrayList<Result>()
                    }
                }
    }

    interface GeoCoderAPI {
        @GET("json")
        fun getAddressOf(@Query("latlng") latlng: String,
                         @Query("sensor") sensor: Boolean,
                         @Query("key") key: String,
                         @Query("language") lang: String): Maybe<Response>
    }

    interface PlacesAPI {
        @GET("nearbysearch/json")
        fun searchNearbyPlaces(@Query("location") location: String,
                               @Query("radius") radius: Int,
                               @Query("keyword") keyword: String,
                               @Query("key") key: String,
                               @Query("language") lang: String): Maybe<Response>
    }

    data class Location(val lat: Double, val lng: Double)
    data class Geometry(val location: Location)
    data class Place(val geometry: Geometry, val name: String, val formatted_address: String)
    data class Response(val status: String, val results: List<Place>)
    data class Result(val address: String, val lat: Double, val lng: Double)

    companion object {
        const val STATUS_OK = "OK"
        const val OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT"
    }
}

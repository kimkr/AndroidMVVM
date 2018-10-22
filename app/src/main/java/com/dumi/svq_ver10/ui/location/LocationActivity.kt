package com.dumi.svq_ver10.ui.location

import android.location.Location
import android.os.Bundle
import android.util.Log
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.ui.BaseActivity
import com.dumi.svq_ver10.util.location.GoogleMapService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class LocationActivity : BaseActivity() {
    @field:[Inject Named("single_location_update")]
    internal lateinit var singleLocationUpdate: Maybe<Location>
    @field:[Inject Named("lastknown_location")]
    internal lateinit var getLastKnownLocation: () -> DoubleArray
    @field:[Inject]
    internal lateinit var googleMapService: GoogleMapService

    override fun getLayout(): Int {
        return R.layout.activity_location
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initMagFragment()
    }

    private fun initMagFragment() {
        val mapFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            singleLocationUpdate.subscribe(
                    { location ->
                        Log.d(TAG, "location $location")
                        focusCamera(map, location)
                        reverseGeoCoding(location)
                        searchNearbyPlaces("세종대")
                    },
                    { e -> Log.e(TAG, "singleLocationUpdate " + e.message) },
                    { Log.d(TAG, "singleLocationUpdate onComplete") })
        }
    }

    private fun focusCamera(map: GoogleMap, location: Location) {
        val position = LatLng(location.latitude, location.longitude)
        map.addMarker(MarkerOptions().position(position))
        map.moveCamera(CameraUpdateFactory.newLatLng(position))
        map.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    private fun reverseGeoCoding(location: Location) {
        googleMapService.getAddressOf(location.latitude, location.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { results ->
                    Log.d(TAG, "reverseGeoCoding results: $results")
                }
    }

    private fun searchNearbyPlaces(keyword: String) {
        val latlng = getLastKnownLocation()
        googleMapService.searchNearbyPlaces(latlng[0], latlng[1], GOOGLE_MAP_SEARCH_RADIUS, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ results ->
                    Log.d(TAG, "searchNearbyPlaces results: $results")
                }, { e ->
                    Log.e(TAG, "searchNearbyPlaces msg: ${e.message}", e)
                }, {

                })
    }

    companion object {
        private val TAG = LocationActivity::class.java.simpleName
        private val GOOGLE_MAP_SEARCH_RADIUS = 1000000 // meters
    }
}
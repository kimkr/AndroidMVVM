package io.github.kimkr.mvvmsample.ui.location

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import io.github.kimkr.mvvmsample.R
import io.github.kimkr.mvvmsample.ui.BaseActivity
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Named

class LocationActivity : BaseActivity() {
    @field:[Inject Named("single_location_update")]
    internal lateinit var singleLocationUpdate: Maybe<Location>
    @field:[Inject Named("lastknown_location")]
    internal lateinit var getLastKnownLocation: () -> DoubleArray
    @field:[Inject]
    lateinit var locationManager: LocationManager

    override fun getLayout(): Int {
        return R.layout.activity_location
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun onResume() {
        super.onResume()
        initMagFragment()
    }

    private fun initMagFragment() {
        val mapFragment = fragmentManager.findFragmentById(R.id.fragment_map) as MapFragment
        mapFragment.getMapAsync { map ->
            singleLocationUpdate.subscribe(
                    { location ->
                        Log.d(TAG, "location $location")
                        focusCamera(map, location)
                        reverseGeoCoding(location)
                    },
                    {},
                    {})
        }
    }

    private fun focusCamera(map: GoogleMap, location: Location) {
        val markerPosition = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(markerPosition)
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(markerPosition))
        map.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    private fun reverseGeoCoding(location: Location) {

    }

    companion object {
        private val TAG = LocationActivity::class.java.simpleName
    }
}
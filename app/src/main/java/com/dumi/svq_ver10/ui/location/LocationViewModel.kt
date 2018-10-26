package com.dumi.svq_ver10.ui.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.location.Location
import android.util.Log
import com.dumi.svq_ver10.di.scopes.ActivityScope
import com.dumi.svq_ver10.persistence.repository.UserRepository
import com.dumi.svq_ver10.util.location.GoogleMapService
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
class LocationViewModel @Inject constructor(private val context: Application,
                                            @Named("single_location_update")
                                            private val singleLocationUpdate: Maybe<Location>,
                                            @Named("lastknown_location")
                                            private val getLastKnownLocation: () -> DoubleArray,
                                            private val userRepository: UserRepository,
                                            private var googleMapService: GoogleMapService)
    : AndroidViewModel(context) {

    var recentViewAction = ViewAction.START_MAP_LOADING
    var mapVisible = ObservableBoolean(true)
    var resultVisible = ObservableBoolean(false)
    var recentSearchVisible = ObservableBoolean(false)
    var chooseCurrentButtonVisible = ObservableBoolean(false)
    var loading = ObservableBoolean(false)
    var currentLocation = ObservableArrayList<Double>()
    var currentAddress = ObservableField<String>("")
    val searchResults: ObservableList<GoogleMapService.Result> = ObservableArrayList()

    fun updateViewState(viewAction: ViewAction) {
        recentViewAction = viewAction
        when (viewAction) {
            ViewAction.START_MAP_LOADING -> {
                loading.set(true)
                mapVisible.set(true)
                resultVisible.set(false)
                chooseCurrentButtonVisible.set(false)
                recentSearchVisible.set(false)
            }
            ViewAction.END_MAP_LOADING -> {
                loading.set(false)
                mapVisible.set(true)
            }
            ViewAction.START_SEARCH -> {
                loading.set(true)
                mapVisible.set(false)
                resultVisible.set(false)
                chooseCurrentButtonVisible.set(false)
                recentSearchVisible.set(false)
            }
            ViewAction.END_SEARCH -> {
                loading.set(false)
                mapVisible.set(false)
                resultVisible.set(true)
                chooseCurrentButtonVisible.set(false)
                recentSearchVisible.set(false)
            }
            ViewAction.CLICK_SEARCH_RESULT -> {
                loading.set(false)
                mapVisible.set(true)
                resultVisible.set(false)
                chooseCurrentButtonVisible.set(false)
                recentSearchVisible.set(false)
            }
            ViewAction.SHOW_RECENT_SEARCH -> {
//                recentSearchVisible.set(true)
            }
            ViewAction.HIDE_RECENT_SEARCH -> {
                recentSearchVisible.set(false)
            }
        }
    }

    fun setCurrentAddress(address: String) {
        currentAddress.set(address)
    }

    fun requestCurrentLocation() {
        singleLocationUpdate.subscribe(
                { location ->
                    Log.d(TAG, "location $location")
                    currentLocation.addAll(0, Arrays.asList(location.latitude, location.longitude))
                    reverseGeoCoding(location)
                },
                { e -> Log.e(TAG, "singleLocationUpdate " + e.message) },
                { Log.d(TAG, "singleLocationUpdate onComplete") })
    }

    fun reverseGeoCoding(location: Location) {
        googleMapService.getAddressOf(location.latitude, location.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { results ->
                    Log.d(TAG, "reverseGeoCoding results: $results")
                    if (!results.isEmpty()) {
                        setCurrentAddress(results[0].address)
                    }
                }
    }

    fun searchNearbyPlaces(keyword: String) {
        val latlng = getLastKnownLocation()
        googleMapService.searchNearbyPlaces(latlng[0], latlng[1], GOOGLE_MAP_SEARCH_RADIUS, keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ results ->
                    Log.d(TAG, "searchNearbyPlaces results: $results")
                    searchResults.clear()
                    searchResults.addAll(results)
                    updateViewState(ViewAction.END_SEARCH)
                }, { e ->
                    Log.e(TAG, "searchNearbyPlaces msg: ${e.message}", e)
                    updateViewState(ViewAction.END_SEARCH)
                })
    }

    companion object {
        private val TAG = LocationViewModel::class.java.simpleName
        private val GOOGLE_MAP_SEARCH_RADIUS = 1000000 // meters
    }
}
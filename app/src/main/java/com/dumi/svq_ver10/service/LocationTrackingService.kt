package com.dumi.svq_ver10.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.support.annotation.RequiresApi
import com.dumi.svq_ver10.persistence.repository.LocationRepository
import com.dumi.svq_ver10.persistence.repository.SettingRepository
import com.dumi.svq_ver10.util.TimeUtil
import io.reactivex.Maybe
import io.reactivex.MaybeOnSubscribe
import javax.inject.Inject

class LocationTrackingService : Service() {

    @field:[Inject]
    lateinit var locationRepository: LocationRepository
    @field:[Inject]
    lateinit var settingRepository: SettingRepository

    override fun onBind(p0: Intent?): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    @SuppressLint("HandlerLeak")
    internal var handler: Handler = object : Handler() { // 새 핸들러를 선언해줍니다.
        @RequiresApi(api = Build.VERSION_CODES.N)
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                var criteria = provideLocationCriteria()
                var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                var provider = provideLocationProvider(locationManager, criteria)
                singleLocationUpdateRequest(locationManager, provider)
                        .subscribe { gps ->
                            var now = TimeUtil.getHour()
                            var allowedTime = settingRepository.getGpsTrakingTime()
                            if (now >= allowedTime.first && now < allowedTime.second) {
                                locationRepository.updateGps(gps.latitude, gps.longitude)
                            }
                        }
            }
            if (settingRepository.isGpsAllowed()) {
                this.sendEmptyMessageDelayed(0, LOCATION_UPDATE_INTERVAL.toLong())
            } else {
                this.sendEmptyMessageDelayed(0, (LOCATION_UPDATE_INTERVAL * 60).toLong())
            }

        }
    }

    fun provideLocationCriteria(): Criteria {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.powerRequirement = Criteria.POWER_LOW
        criteria.isAltitudeRequired = true
        criteria.isBearingRequired = false
        criteria.isSpeedRequired = false
        criteria.isCostAllowed = true
        return criteria
    }

    @SuppressLint("MissingPermission")
    fun provideLocationProvider(locationManager: LocationManager,
                                criteria: Criteria): String {
        var provider = locationManager.getBestProvider(criteria, true)
                ?: LocationManager.NETWORK_PROVIDER
        if (!locationManager.isProviderEnabled(provider) || locationManager.getLastKnownLocation(provider) == null) {
            provider = LocationManager.NETWORK_PROVIDER
        }
        return provider
    }

    @SuppressLint("MissingPermission")
    fun singleLocationUpdateRequest(
            locationManager: LocationManager,
            provider: String): Maybe<Location> {
        return Maybe.create(MaybeOnSubscribe<Location> { emitter ->
            locationManager.requestSingleUpdate(provider, object : LocationListener {
                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                    emitter.onError(Throwable("onProviderDisabled $p0"))
                }

                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        emitter.onSuccess(location)
                    }
                    emitter.onComplete()
                }
            }, null)
        })
    }

    companion object {
        const val LOCATION_UPDATE_INTERVAL = 1000 * 10 // 10 SEC
    }
}
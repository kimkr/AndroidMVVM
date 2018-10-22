package io.github.kimkr.mvvmsample.util.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.di.scopes.ActivityScope
import io.github.kimkr.mvvmsample.ui.location.LocationActivity
import io.reactivex.Maybe
import javax.inject.Named

@Module
class LocationModule {

    @Provides
    @ActivityScope
    fun provideActivityAsContext(activity: LocationActivity): Context {
        return activity
    }

    @Provides
    @ActivityScope
    fun provideLocationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Provides
    @ActivityScope
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
    @Provides
    @ActivityScope
    @Named("location_provider")
    fun provideLocationProvider(locationManager: LocationManager,
                                criteria: Criteria): String {
        var provider = locationManager.getBestProvider(criteria, true)
                ?: LocationManager.NETWORK_PROVIDER
        if (!locationManager.isProviderEnabled(provider) && locationManager.getLastKnownLocation(provider) == null) {
            criteria.accuracy = Criteria.ACCURACY_COARSE
            provider = locationManager.getBestProvider(criteria, true)
                    ?: LocationManager.NETWORK_PROVIDER
        }
        return provider
    }

    @SuppressLint("MissingPermission")
    @Provides
    @Named("single_location_update")
    fun provideSingleLocationUpdateRequest(
            locationManager: LocationManager,
            @Named("location_provider") provider: String): Maybe<Location> {
        return Maybe.create { emitter ->
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
        }
    }

    @SuppressLint("MissingPermission")
    @Provides
    @Named("lastknown_location")
    fun provideLastKnownLocation(locationManager: LocationManager,
                                 @Named("location_provider") provider: String): () -> DoubleArray {
        var defaultLat = 37.56
        var defaultLng = 126.97
        var location = locationManager.getLastKnownLocation(provider)
        return {
            if (location == null) doubleArrayOf(defaultLat, defaultLng)
            else doubleArrayOf(location.latitude, location.longitude)
        }
    }
}
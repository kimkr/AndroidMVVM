package com.dumi.svq_ver10.persistence.di

import android.content.Context
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.cache.PostService
import com.dumi.svq_ver10.persistence.cache.UserService
import com.dumi.svq_ver10.util.location.GoogleMapService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@Module
class APIModule(val context: Context) {

    @Provides
    @Singleton
    @Named("API_URL")
    fun provideAPIUrl(): String {
        return context.getString(R.string.api_url)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named("API_URL") apiUrl: String): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiUrl)
                .build()
    }

    @Provides
    @Singleton
    fun provideUserAPI(retrofit: Retrofit): UserService.UserAPI {
        return retrofit.create(UserService.UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun providePostAPI(retrofit: Retrofit): PostService.PostAPI {
        return retrofit.create(PostService.PostAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideExecutor(): Executor {
        return Executors.newFixedThreadPool(5)
    }

    @Provides
    @Singleton
    fun providePlacesAPI(): GoogleMapService.PlacesAPI {
        val apiUrl = context.getString(R.string.places_api_url)
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiUrl)
                .build()
        return retrofit.create(GoogleMapService.PlacesAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideGeoCoderAPI(): GoogleMapService.GeoCoderAPI {
        val apiUrl = context.getString(R.string.geocoder_api_url)
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiUrl)
                .build()
        return retrofit.create(GoogleMapService.GeoCoderAPI::class.java)
    }

    @Provides
    @Singleton
    @Named("google_maps_key")
    fun provideGoogleMapApiKey(): String {
        return context.getString(R.string.google_maps_key)
    }
}
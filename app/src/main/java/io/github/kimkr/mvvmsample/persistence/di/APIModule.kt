package io.github.kimkr.mvvmsample.persistence.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.kimkr.mvvmsample.R
import io.github.kimkr.mvvmsample.persistence.cache.PostService
import io.github.kimkr.mvvmsample.persistence.cache.UserAPI
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
    fun provideUserAPI(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
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
}
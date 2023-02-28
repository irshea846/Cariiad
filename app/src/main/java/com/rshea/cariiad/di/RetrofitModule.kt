package com.rshea.cariiad.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rshea.cariiad.datastore.remote.UniversityRetrofit
import com.rshea.cariiad.utils.NetworkMonitor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    private fun okHttpClient(networkMonitor: NetworkMonitor): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(RequestInterceptor(networkMonitor))
        .build()

    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    fun provideRetrofit(networkMonitor: NetworkMonitor, url: String, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient(networkMonitor))
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.io()))
    }

    fun provideUniversityService(retrofit: Retrofit.Builder): UniversityRetrofit {
        return retrofit
            .build()
            .create(UniversityRetrofit::class.java)
    }
}
package com.example.weatherdaily.data.remote.network

import com.example.weatherdaily.data.remote.api.GeocodingApi
import com.example.weatherdaily.data.remote.api.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
            .build()
    }

    val weatherApi: WeatherApi by lazy {
        retrofit(WeatherApi.BASE_URL).create(WeatherApi::class.java)
    }

    val geocodingApi: GeocodingApi by lazy {
        retrofit(GeocodingApi.BASE_URL).create(GeocodingApi::class.java)
    }

    private fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

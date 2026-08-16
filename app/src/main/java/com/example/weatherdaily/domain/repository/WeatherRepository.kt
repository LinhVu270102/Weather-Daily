package com.example.weatherdaily.domain.repository

import com.example.weatherdaily.domain.model.WeatherForecast
import com.example.weatherdaily.domain.model.WeatherLocation
import com.example.weatherdaily.domain.model.WeatherMapPoint

interface WeatherRepository {
    suspend fun getForecast(location: WeatherLocation): Result<WeatherForecast>

    suspend fun searchLocations(
        query: String,
        languageCode: String = "vi",
    ): Result<List<WeatherLocation>>

    suspend fun getWeatherGrid(
        center: WeatherLocation,
        gridSize: Int = 5,
        spacingDegrees: Double = 0.18,
    ): Result<List<WeatherMapPoint>>
}

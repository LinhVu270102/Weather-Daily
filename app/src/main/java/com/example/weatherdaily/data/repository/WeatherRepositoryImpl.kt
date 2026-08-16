package com.example.weatherdaily.data.repository

import com.example.weatherdaily.data.mapper.toDomain
import com.example.weatherdaily.data.mapper.toMapPoint
import com.example.weatherdaily.data.remote.api.GeocodingApi
import com.example.weatherdaily.data.remote.api.WeatherApi
import com.example.weatherdaily.data.remote.network.ApiClient
import com.example.weatherdaily.domain.model.WeatherForecast
import com.example.weatherdaily.domain.model.WeatherLocation
import com.example.weatherdaily.domain.repository.WeatherRepository
import com.example.weatherdaily.domain.model.WeatherMapPoint
import java.util.Locale

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi = ApiClient.weatherApi,
    private val geocodingApi: GeocodingApi = ApiClient.geocodingApi,
) : WeatherRepository {

    override suspend fun getForecast(location: WeatherLocation): Result<WeatherForecast> =
        runCatching {
            weatherApi.getForecast(
                latitude = location.latitude,
                longitude = location.longitude,
            ).toDomain(location)
        }

    override suspend fun searchLocations(
        query: String,
        languageCode: String,
    ): Result<List<WeatherLocation>> = runCatching {
        require(query.trim().length >= 2) { "Vui lòng nhập ít nhất 2 ký tự" }
        geocodingApi.searchLocations(
            query = query.trim(),
            language = languageCode,
        ).results.map { it.toDomain() }
    }

    override suspend fun getWeatherGrid(
        center: WeatherLocation,
        gridSize: Int,
        spacingDegrees: Double,
    ): Result<List<WeatherMapPoint>> = runCatching {
        require(gridSize in 3..9 && gridSize % 2 == 1) { "Kích thước lưới phải là số lẻ từ 3 đến 9" }
        val half = gridSize / 2
        val coordinates = buildList {
            for (row in -half..half) {
                for (column in -half..half) {
                    add(
                        center.latitude + row * spacingDegrees to
                            center.longitude + column * spacingDegrees
                    )
                }
            }
        }
        weatherApi.getWeatherGrid(
            latitudes = coordinates.joinToString(",") { String.format(Locale.US, "%.5f", it.first) },
            longitudes = coordinates.joinToString(",") { String.format(Locale.US, "%.5f", it.second) },
        ).map { it.toMapPoint() }
    }
}

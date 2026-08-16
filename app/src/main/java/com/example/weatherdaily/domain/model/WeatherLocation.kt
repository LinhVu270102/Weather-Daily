package com.example.weatherdaily.domain.model

data class WeatherLocation(
    val name: String,
    val country: String,
    val administrativeArea: String?,
    val latitude: Double,
    val longitude: Double,
    val timeZoneId: String,
)

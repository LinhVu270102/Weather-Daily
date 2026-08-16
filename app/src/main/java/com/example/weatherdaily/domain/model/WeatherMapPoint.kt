package com.example.weatherdaily.domain.model

data class WeatherMapPoint(
    val latitude: Double,
    val longitude: Double,
    val temperatureCelsius: Double,
    val condition: WeatherCondition,
    val precipitationProbabilityPercent: Int,
    val windSpeedKmh: Double,
)

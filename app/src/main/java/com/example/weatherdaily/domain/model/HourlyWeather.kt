package com.example.weatherdaily.domain.model

data class HourlyWeather(
    val timeEpochSeconds: Long,
    val temperatureCelsius: Double,
    val apparentTemperatureCelsius: Double,
    val condition: WeatherCondition,
    val humidityPercent: Int,
    val precipitationProbabilityPercent: Int,
    val precipitationMm: Double,
    val windSpeedKmh: Double,
    val isDay: Boolean,
)

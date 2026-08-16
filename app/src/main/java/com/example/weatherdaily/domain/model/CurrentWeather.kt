package com.example.weatherdaily.domain.model

data class CurrentWeather(
    val observedAtEpochSeconds: Long,
    val temperatureCelsius: Double,
    val apparentTemperatureCelsius: Double,
    val condition: WeatherCondition,
    val humidityPercent: Int,
    val precipitationProbabilityPercent: Int,
    val windSpeedKmh: Double,
    val windDirectionDegrees: Int,
    val pressureHpa: Double,
    val visibilityKm: Double,
    val uvIndex: Double,
    val isDay: Boolean,
)

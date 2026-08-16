package com.example.weatherdaily.domain.model

data class DailyWeather(
    /** Thời điểm bắt đầu ngày theo múi giờ của địa điểm. */
    val dateEpochSeconds: Long,
    val condition: WeatherCondition,
    val minimumTemperatureCelsius: Double,
    val maximumTemperatureCelsius: Double,
    val precipitationProbabilityPercent: Int,
    val precipitationMm: Double,
    val maximumWindSpeedKmh: Double,
    val uvIndexMax: Double,
    val sunriseEpochSeconds: Long,
    val sunsetEpochSeconds: Long,
)

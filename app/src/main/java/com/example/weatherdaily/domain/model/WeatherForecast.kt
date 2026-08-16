package com.example.weatherdaily.domain.model

/** Dữ liệu thời tiết hoàn chỉnh mà tầng UI sẽ sử dụng. */
data class WeatherForecast(
    val location: WeatherLocation,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
    val lastUpdatedEpochSeconds: Long,
)

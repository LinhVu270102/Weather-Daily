package com.example.weatherdaily.domain.model

/** Trạng thái thời tiết độc lập với mã trạng thái của từng nhà cung cấp API. */
enum class WeatherCondition {
    CLEAR,
    PARTLY_CLOUDY,
    CLOUDY,
    FOG,
    DRIZZLE,
    RAIN,
    HEAVY_RAIN,
    SNOW,
    THUNDERSTORM,
    UNKNOWN,
}

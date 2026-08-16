package com.example.weatherdaily.ui.map

import com.example.weatherdaily.domain.model.WeatherLocation
import com.example.weatherdaily.domain.model.WeatherForecast
import com.example.weatherdaily.domain.model.WeatherMapPoint

data class MapUiState(
    val isLoading: Boolean = false,
    val selectedLayer: WeatherMapLayer = WeatherMapLayer.PRECIPITATION,
    val selectedLocation: WeatherLocation? = null,
    val forecast: WeatherForecast? = null,
    val gridPoints: List<WeatherMapPoint> = emptyList(),
    val isFollowingCurrentLocation: Boolean = false,
    val errorMessage: String? = null,
)

enum class WeatherMapLayer {
    PRECIPITATION,
    TEMPERATURE,
    WIND,
    CLOUDS,
}

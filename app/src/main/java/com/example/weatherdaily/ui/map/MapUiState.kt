package com.example.weatherdaily.ui.map

import com.example.weatherdaily.domain.model.WeatherLocation

data class MapUiState(
    val isLoading: Boolean = false,
    val selectedLayer: WeatherMapLayer = WeatherMapLayer.PRECIPITATION,
    val selectedLocation: WeatherLocation? = null,
    val isFollowingCurrentLocation: Boolean = false,
    val errorMessage: String? = null,
)

enum class WeatherMapLayer {
    PRECIPITATION,
    TEMPERATURE,
    WIND,
    CLOUDS,
}

package com.example.weatherdaily.ui.forecast

import com.example.weatherdaily.domain.model.DailyWeather
import com.example.weatherdaily.domain.model.WeatherLocation

data class ForecastUiState(
    val isLoading: Boolean = true,
    val location: WeatherLocation? = null,
    val dailyForecast: List<DailyWeather> = emptyList(),
    val selectedDayIndex: Int = 0,
    val errorMessage: String? = null,
)

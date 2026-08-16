package com.example.weatherdaily.ui.home

import com.example.weatherdaily.domain.model.WeatherForecast

data class HomeUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val forecast: WeatherForecast? = null,
    val selectedHourlyIndex: Int = 0,
    val errorMessage: String? = null,
)

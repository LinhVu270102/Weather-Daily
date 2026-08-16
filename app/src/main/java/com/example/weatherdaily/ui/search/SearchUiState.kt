package com.example.weatherdaily.ui.search

import com.example.weatherdaily.domain.model.WeatherLocation

data class SearchUiState(
    val query: String = "",
    val isSearching: Boolean = false,
    val searchResults: List<WeatherLocation> = emptyList(),
    val recentLocations: List<WeatherLocation> = emptyList(),
    val errorMessage: String? = null,
)

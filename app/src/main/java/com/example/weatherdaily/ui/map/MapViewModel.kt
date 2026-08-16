package com.example.weatherdaily.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdaily.data.repository.WeatherRepositoryImpl
import com.example.weatherdaily.domain.model.WeatherLocation
import com.example.weatherdaily.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MapViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun loadWeather(location: WeatherLocation) {
        val current = _uiState.value.selectedLocation
        if (_uiState.value.isLoading || (
                current?.latitude == location.latitude &&
                    current.longitude == location.longitude &&
                    _uiState.value.forecast != null
                )
        ) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, selectedLocation = location, errorMessage = null)
            }
            val (forecastResult, gridResult) = coroutineScope {
                val forecastRequest = async { repository.getForecast(location) }
                val gridRequest = async { repository.getWeatherGrid(location) }
                forecastRequest.await() to gridRequest.await()
            }
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    selectedLocation = location,
                    forecast = forecastResult.getOrNull(),
                    gridPoints = gridResult.getOrDefault(emptyList()),
                    errorMessage = forecastResult.exceptionOrNull()?.message
                        ?: gridResult.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun selectLayer(layer: WeatherMapLayer) {
        _uiState.update { it.copy(selectedLayer = layer) }
    }

    fun selectLocation(location: WeatherLocation) {
        _uiState.update { it.copy(isFollowingCurrentLocation = false) }
        loadWeather(location)
    }

    fun followCurrentLocation(enabled: Boolean) {
        _uiState.update { it.copy(isFollowingCurrentLocation = enabled) }
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun showError(message: String) {
        _uiState.update { it.copy(isLoading = false, errorMessage = message) }
    }
}

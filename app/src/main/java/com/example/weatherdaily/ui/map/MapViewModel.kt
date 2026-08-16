package com.example.weatherdaily.ui.map

import androidx.lifecycle.ViewModel
import com.example.weatherdaily.domain.model.WeatherLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun selectLayer(layer: WeatherMapLayer) {
        _uiState.update { it.copy(selectedLayer = layer) }
    }

    fun selectLocation(location: WeatherLocation) {
        _uiState.update {
            it.copy(selectedLocation = location, isFollowingCurrentLocation = false)
        }
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

package com.example.weatherdaily.ui.home

import androidx.lifecycle.ViewModel
import com.example.weatherdaily.domain.model.WeatherForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun selectHour(index: Int) {
        if (index < 0) return
        _uiState.update { it.copy(selectedHourlyIndex = index) }
    }

    fun startRefreshing() {
        _uiState.update { it.copy(isRefreshing = true, errorMessage = null) }
    }

    fun showForecast(forecast: WeatherForecast) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isRefreshing = false,
                forecast = forecast,
                errorMessage = null,
            )
        }
    }

    fun showError(message: String) {
        _uiState.update {
            it.copy(isLoading = false, isRefreshing = false, errorMessage = message)
        }
    }
}

package com.example.weatherdaily.ui.forecast

import androidx.lifecycle.ViewModel
import com.example.weatherdaily.domain.model.DailyWeather
import com.example.weatherdaily.domain.model.WeatherLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForecastViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ForecastUiState())
    val uiState: StateFlow<ForecastUiState> = _uiState.asStateFlow()

    fun selectDay(index: Int) {
        if (index !in _uiState.value.dailyForecast.indices) return
        _uiState.update { it.copy(selectedDayIndex = index) }
    }

    fun showForecast(location: WeatherLocation, days: List<DailyWeather>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                location = location,
                dailyForecast = days,
                selectedDayIndex = 0,
                errorMessage = null,
            )
        }
    }

    fun showError(message: String) {
        _uiState.update { it.copy(isLoading = false, errorMessage = message) }
    }
}

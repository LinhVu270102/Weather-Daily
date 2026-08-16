package com.example.weatherdaily.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdaily.data.repository.WeatherRepositoryImpl
import com.example.weatherdaily.domain.model.WeatherLocation
import com.example.weatherdaily.domain.repository.WeatherRepository
import com.example.weatherdaily.domain.model.WeatherForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: WeatherRepository = WeatherRepositoryImpl(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadWeather(DEFAULT_LOCATION)
    }

    fun loadWeather(location: WeatherLocation) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = it.forecast == null, errorMessage = null) }
            repository.getForecast(location)
                .onSuccess(::showForecast)
                .onFailure { showError(it.message ?: "Không thể tải dữ liệu thời tiết") }
        }
    }

    fun selectHour(index: Int) {
        if (index < 0) return
        _uiState.update { it.copy(selectedHourlyIndex = index) }
    }

    fun startRefreshing() {
        _uiState.update { it.copy(isRefreshing = true, errorMessage = null) }
        loadWeather(_uiState.value.forecast?.location ?: DEFAULT_LOCATION)
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

    private companion object {
        val DEFAULT_LOCATION = WeatherLocation(
            name = "Đà Nẵng",
            country = "Việt Nam",
            administrativeArea = "Đà Nẵng",
            latitude = 16.0678,
            longitude = 108.2208,
            timeZoneId = "Asia/Ho_Chi_Minh",
        )
    }
}

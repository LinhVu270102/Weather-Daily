package com.example.weatherdaily.ui.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setTemperatureUnit(unit: TemperatureUnit) {
        _uiState.update { it.copy(temperatureUnit = unit) }
    }

    fun setWindSpeedUnit(unit: WindSpeedUnit) {
        _uiState.update { it.copy(windSpeedUnit = unit) }
    }

    fun setThemeMode(mode: ThemeMode) {
        _uiState.update { it.copy(themeMode = mode) }
    }

    fun setLanguage(languageCode: String) {
        _uiState.update { it.copy(languageCode = languageCode) }
    }

    fun setWeatherNotifications(enabled: Boolean) {
        _uiState.update { it.copy(weatherNotificationsEnabled = enabled) }
    }

    fun setSevereWeatherAlerts(enabled: Boolean) {
        _uiState.update { it.copy(severeWeatherAlertsEnabled = enabled) }
    }
}

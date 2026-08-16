package com.example.weatherdaily.ui.settings

data class SettingsUiState(
    val temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val windSpeedUnit: WindSpeedUnit = WindSpeedUnit.KILOMETERS_PER_HOUR,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val languageCode: String = "vi",
    val weatherNotificationsEnabled: Boolean = false,
    val severeWeatherAlertsEnabled: Boolean = true,
)

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT,
}

enum class WindSpeedUnit {
    KILOMETERS_PER_HOUR,
    METERS_PER_SECOND,
    MILES_PER_HOUR,
}

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
}

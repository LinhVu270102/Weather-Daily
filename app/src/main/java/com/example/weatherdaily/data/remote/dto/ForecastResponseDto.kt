package com.example.weatherdaily.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String?,
    val elevation: Double?,
    val current: CurrentWeatherDto?,
    val hourly: HourlyWeatherDto?,
    val daily: DailyWeatherDto?,
)

data class CurrentWeatherDto(
    val time: String,
    @SerializedName("temperature_2m") val temperature: Double?,
    @SerializedName("apparent_temperature") val apparentTemperature: Double?,
    @SerializedName("relative_humidity_2m") val humidity: Int?,
    @SerializedName("precipitation_probability") val precipitationProbability: Int?,
    @SerializedName("weather_code") val weatherCode: Int?,
    @SerializedName("pressure_msl") val pressure: Double?,
    @SerializedName("wind_speed_10m") val windSpeed: Double?,
    @SerializedName("wind_direction_10m") val windDirection: Int?,
    val visibility: Double?,
    @SerializedName("uv_index") val uvIndex: Double?,
    @SerializedName("is_day") val isDay: Int?,
)

data class HourlyWeatherDto(
    val time: List<String> = emptyList(),
    @SerializedName("temperature_2m") val temperature: List<Double> = emptyList(),
    @SerializedName("apparent_temperature") val apparentTemperature: List<Double> = emptyList(),
    @SerializedName("relative_humidity_2m") val humidity: List<Int> = emptyList(),
    @SerializedName("precipitation_probability") val precipitationProbability: List<Int> = emptyList(),
    val precipitation: List<Double> = emptyList(),
    @SerializedName("weather_code") val weatherCode: List<Int> = emptyList(),
    @SerializedName("wind_speed_10m") val windSpeed: List<Double> = emptyList(),
    @SerializedName("is_day") val isDay: List<Int> = emptyList(),
)

data class DailyWeatherDto(
    val time: List<String> = emptyList(),
    @SerializedName("weather_code") val weatherCode: List<Int> = emptyList(),
    @SerializedName("temperature_2m_max") val maximumTemperature: List<Double> = emptyList(),
    @SerializedName("temperature_2m_min") val minimumTemperature: List<Double> = emptyList(),
    @SerializedName("precipitation_probability_max") val precipitationProbability: List<Int> = emptyList(),
    @SerializedName("precipitation_sum") val precipitationSum: List<Double> = emptyList(),
    @SerializedName("wind_speed_10m_max") val maximumWindSpeed: List<Double> = emptyList(),
    @SerializedName("uv_index_max") val maximumUvIndex: List<Double> = emptyList(),
    val sunrise: List<String> = emptyList(),
    val sunset: List<String> = emptyList(),
)

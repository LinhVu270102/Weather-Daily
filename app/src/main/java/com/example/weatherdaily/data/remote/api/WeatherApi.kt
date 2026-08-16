package com.example.weatherdaily.data.remote.api

import com.example.weatherdaily.data.remote.dto.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = CURRENT_FIELDS,
        @Query("hourly") hourly: String = HOURLY_FIELDS,
        @Query("daily") daily: String = DAILY_FIELDS,
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 7,
    ): ForecastResponseDto

    @GET("v1/forecast")
    suspend fun getWeatherGrid(
        @Query("latitude") latitudes: String,
        @Query("longitude") longitudes: String,
        @Query("current") current: String = GRID_FIELDS,
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 1,
    ): List<ForecastResponseDto>

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/"

        private const val CURRENT_FIELDS =
            "temperature_2m,relative_humidity_2m,apparent_temperature,is_day," +
                "precipitation_probability,weather_code,pressure_msl,wind_speed_10m," +
                "wind_direction_10m,visibility,uv_index"

        private const val HOURLY_FIELDS =
            "temperature_2m,relative_humidity_2m,apparent_temperature," +
                "precipitation_probability,precipitation,weather_code,wind_speed_10m,is_day"

        private const val DAILY_FIELDS =
            "weather_code,temperature_2m_max,temperature_2m_min," +
                "precipitation_probability_max,precipitation_sum,wind_speed_10m_max," +
                "uv_index_max,sunrise,sunset"

        private const val GRID_FIELDS =
            "temperature_2m,precipitation_probability,weather_code,wind_speed_10m"
    }
}

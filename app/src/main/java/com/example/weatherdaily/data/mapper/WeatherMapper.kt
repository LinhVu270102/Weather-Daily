package com.example.weatherdaily.data.mapper

import com.example.weatherdaily.data.remote.dto.CurrentWeatherDto
import com.example.weatherdaily.data.remote.dto.DailyWeatherDto
import com.example.weatherdaily.data.remote.dto.ForecastResponseDto
import com.example.weatherdaily.data.remote.dto.HourlyWeatherDto
import com.example.weatherdaily.data.remote.dto.LocationDto
import com.example.weatherdaily.domain.model.CurrentWeather
import com.example.weatherdaily.domain.model.DailyWeather
import com.example.weatherdaily.domain.model.HourlyWeather
import com.example.weatherdaily.domain.model.WeatherCondition
import com.example.weatherdaily.domain.model.WeatherForecast
import com.example.weatherdaily.domain.model.WeatherLocation
import com.example.weatherdaily.domain.model.WeatherMapPoint
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun LocationDto.toDomain(): WeatherLocation = WeatherLocation(
    name = name,
    country = country.orEmpty(),
    administrativeArea = admin1 ?: admin2,
    latitude = latitude,
    longitude = longitude,
    timeZoneId = timezone ?: "UTC",
)

fun ForecastResponseDto.toDomain(location: WeatherLocation): WeatherForecast {
    val currentDomain = requireNotNull(current) { "API không trả về thời tiết hiện tại" }
        .toDomain(utcOffsetSeconds)

    return WeatherForecast(
        location = location,
        current = currentDomain,
        hourly = hourly?.toDomain(utcOffsetSeconds).orEmpty(),
        daily = daily?.toDomain(utcOffsetSeconds).orEmpty(),
        lastUpdatedEpochSeconds = currentDomain.observedAtEpochSeconds,
    )
}

fun ForecastResponseDto.toMapPoint(): WeatherMapPoint {
    val currentValue = requireNotNull(current) { "API không trả về dữ liệu lưới hiện tại" }
    return WeatherMapPoint(
        latitude = latitude,
        longitude = longitude,
        temperatureCelsius = requireNotNull(currentValue.temperature),
        condition = currentValue.weatherCode.toWeatherCondition(),
        precipitationProbabilityPercent = currentValue.precipitationProbability ?: 0,
        windSpeedKmh = currentValue.windSpeed ?: 0.0,
    )
}

private fun CurrentWeatherDto.toDomain(utcOffsetSeconds: Int) = CurrentWeather(
    observedAtEpochSeconds = time.toEpochSeconds(utcOffsetSeconds),
    temperatureCelsius = requireNotNull(temperature),
    apparentTemperatureCelsius = requireNotNull(apparentTemperature),
    condition = weatherCode.toWeatherCondition(),
    humidityPercent = requireNotNull(humidity),
    precipitationProbabilityPercent = precipitationProbability ?: 0,
    windSpeedKmh = requireNotNull(windSpeed),
    windDirectionDegrees = requireNotNull(windDirection),
    pressureHpa = requireNotNull(pressure),
    visibilityKm = requireNotNull(visibility) / 1_000.0,
    uvIndex = uvIndex ?: 0.0,
    isDay = isDay == 1,
)

private fun HourlyWeatherDto.toDomain(utcOffsetSeconds: Int): List<HourlyWeather> =
    time.indices.mapNotNull { index ->
        val temperatureValue = temperature.getOrNull(index) ?: return@mapNotNull null
        HourlyWeather(
            timeEpochSeconds = time[index].toEpochSeconds(utcOffsetSeconds),
            temperatureCelsius = temperatureValue,
            apparentTemperatureCelsius = apparentTemperature.getOrNull(index) ?: temperatureValue,
            condition = weatherCode.getOrNull(index).toWeatherCondition(),
            humidityPercent = humidity.getOrNull(index) ?: 0,
            precipitationProbabilityPercent = precipitationProbability.getOrNull(index) ?: 0,
            precipitationMm = precipitation.getOrNull(index) ?: 0.0,
            windSpeedKmh = windSpeed.getOrNull(index) ?: 0.0,
            isDay = isDay.getOrNull(index) == 1,
        )
    }

private fun DailyWeatherDto.toDomain(utcOffsetSeconds: Int): List<DailyWeather> =
    time.indices.mapNotNull { index ->
        val minimum = minimumTemperature.getOrNull(index) ?: return@mapNotNull null
        val maximum = maximumTemperature.getOrNull(index) ?: return@mapNotNull null
        DailyWeather(
            dateEpochSeconds = time[index].toEpochSeconds(utcOffsetSeconds, DATE_PATTERN),
            condition = weatherCode.getOrNull(index).toWeatherCondition(),
            minimumTemperatureCelsius = minimum,
            maximumTemperatureCelsius = maximum,
            precipitationProbabilityPercent = precipitationProbability.getOrNull(index) ?: 0,
            precipitationMm = precipitationSum.getOrNull(index) ?: 0.0,
            maximumWindSpeedKmh = maximumWindSpeed.getOrNull(index) ?: 0.0,
            uvIndexMax = maximumUvIndex.getOrNull(index) ?: 0.0,
            sunriseEpochSeconds = sunrise.getOrNull(index)?.toEpochSeconds(utcOffsetSeconds) ?: 0L,
            sunsetEpochSeconds = sunset.getOrNull(index)?.toEpochSeconds(utcOffsetSeconds) ?: 0L,
        )
    }

fun Int?.toWeatherCondition(): WeatherCondition = when (this) {
    0 -> WeatherCondition.CLEAR
    1, 2 -> WeatherCondition.PARTLY_CLOUDY
    3 -> WeatherCondition.CLOUDY
    45, 48 -> WeatherCondition.FOG
    51, 53, 55, 56, 57 -> WeatherCondition.DRIZZLE
    61, 63, 66, 80, 81 -> WeatherCondition.RAIN
    65, 67, 82 -> WeatherCondition.HEAVY_RAIN
    71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOW
    95, 96, 99 -> WeatherCondition.THUNDERSTORM
    else -> WeatherCondition.UNKNOWN
}

private fun String.toEpochSeconds(
    utcOffsetSeconds: Int,
    pattern: String = DATE_TIME_PATTERN,
): Long {
    val sign = if (utcOffsetSeconds >= 0) "+" else "-"
    val absoluteOffset = kotlin.math.abs(utcOffsetSeconds)
    val hours = absoluteOffset / 3_600
    val minutes = absoluteOffset % 3_600 / 60
    val zone = TimeZone.getTimeZone("GMT%s%02d:%02d".format(sign, hours, minutes))
    val formatter = SimpleDateFormat(pattern, Locale.US).apply {
        isLenient = false
        timeZone = zone
    }
    return requireNotNull(formatter.parse(this)) { "Thời gian không hợp lệ: $this" }.time / 1_000
}

private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm"
private const val DATE_PATTERN = "yyyy-MM-dd"

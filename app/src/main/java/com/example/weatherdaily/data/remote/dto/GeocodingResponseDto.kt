package com.example.weatherdaily.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GeocodingResponseDto(
    val results: List<LocationDto> = emptyList(),
)

data class LocationDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double?,
    val timezone: String?,
    val country: String?,
    @SerializedName("country_code") val countryCode: String?,
    val admin1: String?,
    val admin2: String?,
)

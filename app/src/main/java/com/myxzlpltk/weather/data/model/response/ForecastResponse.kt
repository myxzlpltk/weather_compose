package com.myxzlpltk.weather.data.model.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(

    @field:SerializedName("elevation")
    val elevation: Int,

    @field:SerializedName("hourly_units")
    val hourlyUnits: ForecastResponseHourlyUnits,

    @field:SerializedName("generationtime_ms")
    val generationtimeMs: Double,

    @field:SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,

    @field:SerializedName("timezone")
    val timezone: String,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int,

    @field:SerializedName("hourly")
    val hourly: ForecastResponseHourly,

    @field:SerializedName("longitude")
    val longitude: Double
)

data class ForecastResponseHourly(

    @field:SerializedName("temperature_2m")
    val temperature2m: List<Double>,

    @field:SerializedName("relativehumidity_2m")
    val relativehumidity2m: List<Int>,

    @field:SerializedName("weathercode")
    val weathercode: List<Int>,

    @field:SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>,

    @field:SerializedName("windspeed_10m")
    val windspeed10m: List<Double>,

    @field:SerializedName("time")
    val time: List<String>
)

data class ForecastResponseHourlyUnits(

    @field:SerializedName("temperature_2m")
    val temperature2m: String,

    @field:SerializedName("relativehumidity_2m")
    val relativehumidity2m: String,

    @field:SerializedName("weathercode")
    val weathercode: String,

    @field:SerializedName("precipitation_probability")
    val precipitationProbability: String,

    @field:SerializedName("windspeed_10m")
    val windspeed10m: String,

    @field:SerializedName("time")
    val time: String
)

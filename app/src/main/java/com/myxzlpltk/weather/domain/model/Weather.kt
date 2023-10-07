package com.myxzlpltk.weather.domain.model

data class Weather(
    val id: Long,
    val weatherCode: Int,
    val temperature: Double,
    val humidity: Int,
    val precipitationProbability: Int,
    val windSpeed: Double,
)

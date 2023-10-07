package com.myxzlpltk.weather.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weathers")
data class LocalWeather(
    @PrimaryKey
    val id: Long,
    val weatherCode: Int,
    val temperature: Double,
    val humidity: Int,
    val precipitationProbability: Int,
    val windSpeed: Double,
    val cityName: String,
)
package com.myxzlpltk.weather.domain.repository

import com.myxzlpltk.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface WeatherRepository {
    suspend fun fetchForecast(latitude: Double, longitude: Double, cityName: String): List<Weather>

    fun getCurrentWeather(date: LocalDateTime): Flow<Weather?>
}
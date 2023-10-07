package com.myxzlpltk.weather.domain.repository

import com.myxzlpltk.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun fetchForecast(): List<Weather>

    suspend fun getCurrentWeather(): Weather
}
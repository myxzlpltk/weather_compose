package com.myxzlpltk.weather.data.repository

import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.repository.WeatherRepository

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun fetchForecast(): List<Weather> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeather(): Weather {
        TODO("Not yet implemented")
    }
}
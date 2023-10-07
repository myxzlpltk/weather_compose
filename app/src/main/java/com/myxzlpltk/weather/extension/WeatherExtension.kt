package com.myxzlpltk.weather.extension

import com.myxzlpltk.weather.data.model.local.LocalWeather
import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.model.WeatherStatus

fun LocalWeather.toDomain() = Weather(
    id = id,
    weatherCode = weatherCode,
    weatherStatus = WeatherStatus.fromCode(weatherCode),
    temperature = temperature,
    humidity = humidity,
    precipitationProbability = precipitationProbability,
    windSpeed = windSpeed,
    cityName = cityName,
)

fun Weather.toLocal() = LocalWeather(
    id = id,
    weatherCode = weatherCode,
    temperature = temperature,
    humidity = humidity,
    precipitationProbability = precipitationProbability,
    windSpeed = windSpeed,
    cityName = cityName,
)

fun dummyWeather() = Weather(
    id = 1689327011000,
    weatherCode = 0,
    weatherStatus = WeatherStatus.CLEAR_SKY,
    temperature = 30.3,
    humidity = 97,
    precipitationProbability = 10,
    windSpeed = 5.0,
    cityName = "New York"
)
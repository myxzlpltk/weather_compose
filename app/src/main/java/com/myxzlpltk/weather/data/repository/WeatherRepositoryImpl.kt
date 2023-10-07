package com.myxzlpltk.weather.data.repository

import androidx.room.withTransaction
import com.myxzlpltk.weather.data.service.WeatherDao
import com.myxzlpltk.weather.data.service.WeatherDatabase
import com.myxzlpltk.weather.data.service.WeatherService
import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.model.WeatherStatus
import com.myxzlpltk.weather.domain.repository.WeatherRepository
import com.myxzlpltk.weather.extension.toDomain
import com.myxzlpltk.weather.extension.toLocal
import com.myxzlpltk.weather.util.toEpochMilli
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao,
    private val database: WeatherDatabase
) : WeatherRepository {

    override suspend fun fetchForecast(
        latitude: Double,
        longitude: Double,
        cityName: String
    ): List<Weather> {
        val timeZone = ZoneId.systemDefault().id
        val forecast = weatherService.getForecast(latitude, longitude, timeZone)
        val weathers = forecast.hourly.time.mapIndexed { i, time ->
            Weather(
                id = LocalDateTime.parse(time).toEpochMilli(),
                weatherCode = forecast.hourly.weathercode[i],
                weatherStatus = WeatherStatus.fromCode(forecast.hourly.weathercode[i]),
                temperature = forecast.hourly.temperature2m[i],
                humidity = forecast.hourly.relativehumidity2m[i],
                precipitationProbability = forecast.hourly.precipitationProbability[i],
                windSpeed = forecast.hourly.windspeed10m[i],
                cityName = cityName
            )
        }

        database.withTransaction {
            weatherDao.deleteAll()
            weatherDao.insertAll(weathers.map { it.toLocal() })
        }

        return weathers
    }

    override fun getCurrentWeather(date: LocalDateTime): Flow<Weather?> {
        val lowerDate = date.truncatedTo(ChronoUnit.HOURS)
        val upperDate = date.truncatedTo(ChronoUnit.HOURS).plusHours(1)

        val localWeatherFlow = if (date.minute < 30) {
            weatherDao.getCurrentWeather(lowerDate, upperDate)
        } else {
            weatherDao.getCurrentWeather(upperDate, lowerDate)
        }

        return localWeatherFlow.map { it?.toDomain() }
    }
}
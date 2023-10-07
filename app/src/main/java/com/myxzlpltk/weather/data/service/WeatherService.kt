package com.myxzlpltk.weather.data.service

import com.myxzlpltk.weather.data.model.response.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("v1/forecast?hourly=temperature_2m,relativehumidity_2m,precipitation_probability,weathercode,windspeed_10m")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("timezone") timeZone: String,
    ): ForecastResponse
}
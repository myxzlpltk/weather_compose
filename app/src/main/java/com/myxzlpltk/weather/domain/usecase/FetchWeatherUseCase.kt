package com.myxzlpltk.weather.domain.usecase

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.myxzlpltk.weather.domain.repository.WeatherRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(
    @ApplicationContext context: Context,
    private val weatherRepository: WeatherRepository
) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    operator fun invoke() {

    }
}
package com.myxzlpltk.weather.domain.usecase

import com.myxzlpltk.weather.domain.model.Weather
import com.myxzlpltk.weather.domain.repository.WeatherRepository
import com.myxzlpltk.weather.util.countDownFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import java.time.LocalDateTime
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentWeather(): Flow<Weather?> {
        return countDownFlow(60_000).flatMapLatest(weatherRepository::getCurrentWeather)
    }

    suspend fun getCurrentWeatherNow(): Weather? {
        val date = LocalDateTime.now()

        return weatherRepository.getCurrentWeatherNow(date)
    }
}
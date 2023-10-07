package com.myxzlpltk.weather.domain.model

import androidx.annotation.StringRes
import com.myxzlpltk.weather.R

enum class WeatherStatus(private val codes: IntArray, @StringRes val description: Int) {
    CLEAR_SKY(intArrayOf(0), R.string.clear_sky),
    CLOUDY(intArrayOf(1, 2, 3), R.string.cloudy),
    FOGGY(intArrayOf(45, 48), R.string.foggy),
    DRIZZLE(intArrayOf(51, 53, 55), R.string.drizzle),
    FREEZING_DRIZZLE(intArrayOf(56, 57), R.string.freezing_drizzle),
    RAIN(intArrayOf(61, 63, 65), R.string.rain),
    FREEZING_RAIN(intArrayOf(66, 67), R.string.freezing_rain),
    SNOW(intArrayOf(71, 73, 75), R.string.snow),
    SNOW_GRAINS(intArrayOf(77), R.string.snow_grains),
    RAIN_SHOWERS(intArrayOf(80, 81, 82), R.string.rain_showers),
    SNOW_SHOWERS(intArrayOf(85, 86), R.string.snow_showers),
    THUNDERSTORM(intArrayOf(95), R.string.thunderstorm),
    THUNDERSTORM_HAIL(intArrayOf(96, 99), R.string.thunderstorm_hail),
    UNKNOWN(intArrayOf(-1), R.string.unknown);

    companion object {
        fun fromCode(code: Int): WeatherStatus {
            return values().find { it.codes.contains(code) } ?: UNKNOWN
        }
    }
}
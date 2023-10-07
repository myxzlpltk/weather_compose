package com.myxzlpltk.weather.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.myxzlpltk.weather.R

enum class WeatherStatus(
    private val codes: IntArray,
    @StringRes val description: Int,
    @DrawableRes val image: Int,
) {
    CLEAR_SKY(intArrayOf(0), R.string.clear_sky, R.drawable.clear_sky),
    CLOUDY(intArrayOf(1, 2, 3), R.string.cloudy, R.drawable.cloudy),
    FOGGY(intArrayOf(45, 48), R.string.foggy, R.drawable.foggy),
    DRIZZLE(intArrayOf(51, 53, 55), R.string.drizzle, R.drawable.drizzle),
    FREEZING_DRIZZLE(intArrayOf(56, 57), R.string.freezing_drizzle, R.drawable.freezing_drizzle),
    RAIN(intArrayOf(61, 63, 65), R.string.rain, R.drawable.rain),
    FREEZING_RAIN(intArrayOf(66, 67), R.string.freezing_rain, R.drawable.freezing_rain),
    SNOW(intArrayOf(71, 73, 75), R.string.snow, R.drawable.snow),
    SNOW_GRAINS(intArrayOf(77), R.string.snow_grains, R.drawable.snow_grains),
    RAIN_SHOWERS(intArrayOf(80, 81, 82), R.string.rain_showers, R.drawable.rain_showers),
    SNOW_SHOWERS(intArrayOf(85, 86), R.string.snow_showers, R.drawable.snow_showers),
    THUNDERSTORM(intArrayOf(95), R.string.thunderstorm, R.drawable.thunderstorm),
    THUNDERSTORM_HAIL(intArrayOf(96, 99), R.string.thunderstorm_hail, R.drawable.thunderstorm_hail),
    UNKNOWN(intArrayOf(-1), R.string.unknown, R.drawable.unknown);

    companion object {
        fun fromCode(code: Int): WeatherStatus {
            return values().find { it.codes.contains(code) } ?: UNKNOWN
        }
    }
}
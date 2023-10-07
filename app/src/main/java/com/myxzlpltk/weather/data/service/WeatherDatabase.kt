package com.myxzlpltk.weather.data.service

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myxzlpltk.weather.data.model.local.LocalWeather
import com.myxzlpltk.weather.util.Converters

@Database(entities = [LocalWeather::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
package com.myxzlpltk.weather.data.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myxzlpltk.weather.data.model.local.LocalWeather
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weathers WHERE id = :date")
    fun getCurrentWeather(date: LocalDateTime): Flow<LocalWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg weathers: LocalWeather)
}
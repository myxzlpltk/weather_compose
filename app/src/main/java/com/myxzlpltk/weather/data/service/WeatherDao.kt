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

    @Query("SELECT * FROM weathers WHERE id = :date1 OR id = :date2")
    fun getCurrentWeather(date1: LocalDateTime, date2: LocalDateTime): Flow<LocalWeather?>

    @Query("SELECT * FROM weathers WHERE id = :date1 OR id = :date2")
    suspend fun getCurrentWeatherNow(date1: LocalDateTime, date2: LocalDateTime): LocalWeather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weathers: List<LocalWeather>)

    @Query("DELETE FROM weathers")
    suspend fun deleteAll()
}
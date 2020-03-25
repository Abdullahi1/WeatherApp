package com.example.abdullahi.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abdullahi.weatherapp.data.db.entity.CURRENT_WEATHER_ID
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherEntry: CurrentWeatherEntry)

    @Query("Select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeather() : LiveData<CurrentWeatherEntry>

}
package com.example.abdullahi.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abdullahi.weatherapp.data.db.entity.WEATHER_LOCATION_ID
import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation

@Dao
interface WeatherLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherLocation: WeatherLocation)

    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>
}
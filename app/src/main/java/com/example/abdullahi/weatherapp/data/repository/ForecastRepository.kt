package com.example.abdullahi.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation
import com.example.abdullahi.weatherapp.data.db.future.FutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out CurrentWeatherEntry>
    suspend fun getWeatherLocation() : LiveData<WeatherLocation>

    suspend fun getFutureWeatherList(metric: Boolean, startDate: LocalDate) : LiveData<out List<FutureWeatherEntry>>
}
package com.example.abdullahi.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out CurrentWeatherEntry>
}
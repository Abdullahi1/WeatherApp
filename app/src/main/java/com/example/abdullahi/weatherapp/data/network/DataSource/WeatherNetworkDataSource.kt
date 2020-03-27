package com.example.abdullahi.weatherapp.data.network.DataSource

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        units: String
    )
}
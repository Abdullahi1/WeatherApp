package com.example.abdullahi.weatherapp.data.network.dataSource

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.abdullahi.weatherapp.data.db.future.FutureWeatherResponse

interface WeatherNetworkDataSource {

    val downloadCurrentWeather : LiveData<CurrentWeatherResponse>

    val downloadFutureWeather : LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        units: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        units: String,
        forecastDays:String,
        hourly: String
    )
}
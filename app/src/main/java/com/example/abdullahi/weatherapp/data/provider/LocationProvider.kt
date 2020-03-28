package com.example.abdullahi.weatherapp.data.provider

import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean
    suspend fun getPreferredLocationString() : String
}
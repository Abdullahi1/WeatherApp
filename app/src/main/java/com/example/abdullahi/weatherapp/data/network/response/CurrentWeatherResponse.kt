package com.example.abdullahi.weatherapp.data.network.response

import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation
import com.example.abdullahi.weatherapp.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)
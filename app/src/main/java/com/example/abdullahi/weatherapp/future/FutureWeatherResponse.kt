package com.example.abdullahi.weatherapp.future


import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
    //val current: Current,
    //val forecastDaysContainer: List<ForecastDaysContainer>,
    @SerializedName("forecast")
    val forecastDaysContainer: ForecastDaysContainer,
    val location: WeatherLocation
    //val request: Request
)
package com.example.abdullahi.weatherapp.future


import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(
    //Get for each day
    //@SerializedName("2019-09-07")
    val futureWeatherEntry: List<FutureWeatherEntry>
)
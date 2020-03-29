package com.example.abdullahi.weatherapp.data.db.future


data class ForecastDaysContainer(
    //Get for each day
    //@SerializedName("2019-09-07")
    val futureWeatherEntry: List<FutureWeatherEntry>
)
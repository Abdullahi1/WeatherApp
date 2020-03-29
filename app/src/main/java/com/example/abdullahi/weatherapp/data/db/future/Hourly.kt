package com.example.abdullahi.weatherapp.data.db.future


import com.google.gson.annotations.SerializedName

data class Hourly(
    val chanceoffog: Int,
    val chanceoffrost: Int,
    val chanceofhightemp: Int,
    val chanceofovercast: Int,
    val chanceofrain: Int,
    val chanceofremdry: Int,
    val chanceofsnow: Int,
    val chanceofsunshine: Int,
    val chanceofthunder: Int,
    val chanceofwindy: Int,
    val cloudcover: Int,
    val dewpoint: Int,
    val feelslike: Int,
    val heatindex: Int,
    val humidity: Int,
    val precip: Int,
    val pressure: Int,
    val temperature: Int,
    val time: String,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int,
    val windchill: Int,
    val windgust: Int
)
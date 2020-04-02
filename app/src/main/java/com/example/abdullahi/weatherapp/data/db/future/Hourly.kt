package com.example.abdullahi.weatherapp.data.db.future


import com.google.gson.annotations.SerializedName

data class Hourly(
    val chanceoffog: Double,
    val chanceoffrost: Double,
    val chanceofhightemp: Double,
    val chanceofovercast: Double,
    val chanceofrain: Double,
    val chanceofremdry: Double,
    val chanceofsnow: Double,
    val chanceofsunshine: Double,
    val chanceofthunder: Double,
    val chanceofwindy: Double,
    val cloudcover: Double,
    val dewpoDouble: Double,
    val feelslike: Double,
    val heatindex: Double,
    val humidity: Double,
    val precip: Double,
    val pressure: Double,
    val temperature: Double,
    val time: String,
    @SerializedName("uv_index")
    val uvIndex: Double,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Double,
    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,
    @SerializedName("weather_icons")
    val weatherIcons: List<String>,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    val windchill: Double,
    val windgust: Double
)
package com.example.abdullahi.weatherapp.future


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices =[Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    val avgtemp: Int,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    //val hourly: List<Hourly>,
    val maxtemp: Int,
    val mintemp: Int,
    val sunhour: Double,
    val totalsnow: Int,
    @SerializedName("uv_index")
    val uvIndex: Int
)
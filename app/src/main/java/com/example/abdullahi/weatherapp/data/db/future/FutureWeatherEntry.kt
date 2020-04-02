package com.example.abdullahi.weatherapp.data.db.future


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices =[Index(value = ["date"], unique = true)])
data class FutureWeatherEntry(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    val avgtemp: Double,
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Double,
    val hourly: List<Hourly>,
    val maxtemp: Double,
    val mDoubleemp: Double,
    val sunhour: Double,
    val totalsnow: Double,
    @SerializedName("uv_index")
    val uvIndex: Double
)
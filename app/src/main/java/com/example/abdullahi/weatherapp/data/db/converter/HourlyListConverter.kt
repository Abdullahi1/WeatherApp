package com.example.abdullahi.weatherapp.data.db.converter

import androidx.room.TypeConverter
import com.example.abdullahi.weatherapp.data.db.future.Hourly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class HourlyListConverter {

    @TypeConverter
    fun stringToList(data : String): List<Hourly>{
        if (data == null){
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Hourly>>(){}.type
        return  Gson().fromJson(data,listType)
    }

    @TypeConverter
    fun listToStrings(list: List<Hourly>) : String{
        val type = object : TypeToken<List<Hourly>>(){}.type
        return Gson().toJson(list, type)
    }
}
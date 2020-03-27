package com.example.abdullahi.weatherapp.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListConverter {


    @TypeConverter
     fun stringToList(data : String): List<String>{
        if (data == null){
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<String>>(){}.type
         return  Gson().fromJson(data,listType)
    }

    @TypeConverter
    fun listToStrings(list: List<String>) : String{
        val type = object : TypeToken<List<String>>(){}.type
        return Gson().toJson(list, type)
    }
}
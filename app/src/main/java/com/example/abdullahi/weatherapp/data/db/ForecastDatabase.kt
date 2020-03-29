package com.example.abdullahi.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.abdullahi.weatherapp.data.db.converter.HourlyListConverter
import com.example.abdullahi.weatherapp.data.db.converter.ListConverter
import com.example.abdullahi.weatherapp.data.db.converter.LocalDateConverter
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation


@Database(
    entities=[CurrentWeatherEntry::class, WeatherLocation::class],
    version = 1
)
@TypeConverters(ListConverter::class,HourlyListConverter::class,LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase(){
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao() : WeatherLocationDao
    abstract fun futureWeatherDao() : FutureWeatherDao

    companion object{
         private var instance : ForecastDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
                                            Room.databaseBuilder(context.applicationContext,
                                                ForecastDatabase::class.java, "forecastDaysContainer.db")
                                                .build()
    }
}
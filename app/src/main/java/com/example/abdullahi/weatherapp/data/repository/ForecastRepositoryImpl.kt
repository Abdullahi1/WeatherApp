package com.example.abdullahi.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.db.CurrentWeatherDao
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.network.DataSource.WeatherNetworkDataSource
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadCurrentWeather.observeForever{newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData(metric)
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(featchedCurrentWeather: CurrentWeatherResponse)  {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insert(featchedCurrentWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(metric: Boolean){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean){
        val unitVal = if (metric)  "m" else  "f"
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angeles",
            unitVal
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime:ZonedDateTime) : Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}
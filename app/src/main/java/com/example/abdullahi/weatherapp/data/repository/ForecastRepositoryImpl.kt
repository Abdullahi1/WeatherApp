package com.example.abdullahi.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.db.CurrentWeatherDao
import com.example.abdullahi.weatherapp.data.db.WeatherLocationDao
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation
import com.example.abdullahi.weatherapp.data.network.dataSource.WeatherNetworkDataSource
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.abdullahi.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

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

    private fun persistFetchedCurrentWeather(fetchedCurrentWeather: CurrentWeatherResponse)  {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insert(fetchedCurrentWeather.currentWeatherEntry)
            weatherLocationDao.insert(fetchedCurrentWeather.location)
        }
    }

    private suspend fun initWeatherData(metric: Boolean){

        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather(metric)
    }

    private suspend fun fetchCurrentWeather(metric: Boolean){
        val unitVal = if (metric)  "m" else  "f"
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            unitVal
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime:ZonedDateTime) : Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}
package com.example.abdullahi.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.abdullahi.weatherapp.data.db.CurrentWeatherDao
import com.example.abdullahi.weatherapp.data.db.FutureWeatherDao
import com.example.abdullahi.weatherapp.data.db.WeatherLocationDao
import com.example.abdullahi.weatherapp.data.db.entity.CurrentWeatherEntry
import com.example.abdullahi.weatherapp.data.db.entity.WeatherLocation
import com.example.abdullahi.weatherapp.data.network.dataSource.WeatherNetworkDataSource
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.abdullahi.weatherapp.data.provider.LocationProvider
import com.example.abdullahi.weatherapp.data.db.future.FutureWeatherEntry
import com.example.abdullahi.weatherapp.data.db.future.FutureWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {


    init {

        weatherNetworkDataSource.downloadCurrentWeather.observeForever{newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }

        weatherNetworkDataSource.downloadFutureWeather.observeForever{futureWeather ->
            persistFetchedFutureWeather(futureWeather)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO){
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData(metric)
            return@withContext currentWeatherDao.getWeather()
        }
    }

    override suspend fun getFutureWeatherList(metric: Boolean, startDate: LocalDate): LiveData<out List<FutureWeatherEntry>> {
        return withContext(Dispatchers.IO){
            initWeatherData(metric)
            return@withContext futureWeatherDao.getSimpleWeatherForecasts(startDate)
        }
    }

    private fun persistFetchedCurrentWeather(fetchedCurrentWeather: CurrentWeatherResponse)  {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insert(fetchedCurrentWeather.currentWeatherEntry)
            weatherLocationDao.insert(fetchedCurrentWeather.location)
        }
    }

    private fun persistFetchedFutureWeather(futureWeather: FutureWeatherResponse) {
        fun deleteOldForecastData(){
            val today  = LocalDate.now();
            futureWeatherDao.deleteOldEntries(today)
        }
        GlobalScope.launch(Dispatchers.IO){
            deleteOldForecastData()
            val weatherForecastList = futureWeather.forecastDaysContainer.futureWeatherEntry
            futureWeatherDao.insert(weatherForecastList)
            weatherLocationDao.insert(futureWeather.location)
        }
    }

    private suspend fun initWeatherData(metric: Boolean){

        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather(metric)
            fetchFutureWeather(metric)
            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather(metric)

        if (isFutureNeeded()){
            fetchFutureWeather(metric)
        }
    }

    private fun isFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < 7
    }

    private suspend fun fetchFutureWeather(metric: Boolean) {
        val unitVal = if (metric)  "m" else  "f"
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            unitVal,
            "7",
            "1"
        )

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
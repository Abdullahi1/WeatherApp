package com.example.abdullahi.weatherapp.data.network.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abdullahi.weatherapp.data.WeatherApiService
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.abdullahi.weatherapp.future.FutureWeatherResponse
import com.example.abdullahi.weatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadCurrentWeather  = MutableLiveData<CurrentWeatherResponse>()

    private val _downloadFutureWeather = MutableLiveData<FutureWeatherResponse>()

    override val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadCurrentWeather

    override val downloadFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadFutureWeather


    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            val fetchedCurrentWeather = weatherApiService.getCurrentWeatherAsync(location, units).await()
            _downloadCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e : NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection")
        }
    }

    override suspend fun fetchFutureWeather(
        location: String,
        units: String,
        forecastDays: String,
        hourly: String
    ) {
        try {
            val fetchedFutureWeather = weatherApiService
                .getFutureWeatherAsync(location, units, forecastDays, hourly).await()
            _downloadFutureWeather.postValue(fetchedFutureWeather)

        }catch (e : NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection")
        }
    }

}
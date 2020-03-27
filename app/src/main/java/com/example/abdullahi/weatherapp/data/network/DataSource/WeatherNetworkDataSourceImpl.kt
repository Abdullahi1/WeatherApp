package com.example.abdullahi.weatherapp.data.network.DataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.abdullahi.weatherapp.data.WeatherApiService
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.abdullahi.weatherapp.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadCurrentWeather  = MutableLiveData<CurrentWeatherResponse>()

    override val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            val fetchedCurrentWeather = weatherApiService.getCurrentWeather(location, units).await()
            _downloadCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e : NoConnectivityException){
            Log.e("Connectivity", "No Internet Connection")
        }
    }
}
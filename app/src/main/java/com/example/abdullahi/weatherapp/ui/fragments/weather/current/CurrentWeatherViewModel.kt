package com.example.abdullahi.weatherapp.ui.fragments.weather.current

import androidx.lifecycle.ViewModel
import com.example.abdullahi.weatherapp.data.provider.UnitProvider
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository
import com.example.abdullahi.weatherapp.internal.UnitSystem
import com.example.abdullahi.weatherapp.internal.lazyDeferred
import com.example.abdullahi.weatherapp.ui.base.WeatherViewModel

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider : UnitProvider
) : WeatherViewModel(forecastRepository,unitProvider) {

//    private val unitSystem = unitProvider.getUnitSystem()
//
//     val isMetric : Boolean
//        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred{
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }

//    val weatherLocation by lazyDeferred{
//        forecastRepository.getWeatherLocation()
//    }
}

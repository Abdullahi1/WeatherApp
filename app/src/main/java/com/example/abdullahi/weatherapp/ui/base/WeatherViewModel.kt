package com.example.abdullahi.weatherapp.ui.base

import androidx.lifecycle.ViewModel
import com.example.abdullahi.weatherapp.data.provider.UnitProvider
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository
import com.example.abdullahi.weatherapp.internal.UnitSystem
import com.example.abdullahi.weatherapp.internal.lazyDeferred



abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeferred {
        forecastRepository.getWeatherLocation()
    }
}
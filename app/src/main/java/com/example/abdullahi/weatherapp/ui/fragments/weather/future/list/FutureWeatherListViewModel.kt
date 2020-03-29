package com.example.abdullahi.weatherapp.ui.fragments.weather.future.list

import androidx.lifecycle.ViewModel
import com.example.abdullahi.weatherapp.data.provider.UnitProvider
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository
import com.example.abdullahi.weatherapp.internal.lazyDeferred
import com.example.abdullahi.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureWeatherListViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider : UnitProvider
) : WeatherViewModel(forecastRepository,unitProvider) {

    val weather by lazyDeferred{
        forecastRepository.getFutureWeatherList(super.isMetricUnit, LocalDate.now())
    }
}

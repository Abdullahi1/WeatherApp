package com.example.abdullahi.weatherapp.ui.fragments.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.abdullahi.weatherapp.data.provider.UnitProvider
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory (
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository, unitProvider) as T
    }
}
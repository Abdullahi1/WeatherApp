package com.example.abdullahi.weatherapp.ui.fragments.weather.current

import androidx.lifecycle.ViewModel
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository
import com.example.abdullahi.weatherapp.internal.UnitSystem

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val unitSystem = UnitSystem.METRIC
     val isMetric : Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather = forecastRepository.getCurrentWeather(isMetric)
}

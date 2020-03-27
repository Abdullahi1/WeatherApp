package com.example.abdullahi.weatherapp.data.provider

import com.example.abdullahi.weatherapp.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem() : UnitSystem
}
package com.example.abdullahi.weatherapp

import android.app.Application
import com.example.abdullahi.weatherapp.data.WeatherApiService
import com.example.abdullahi.weatherapp.data.db.ForecastDatabase
import com.example.abdullahi.weatherapp.data.network.DataSource.WeatherNetworkDataSource
import com.example.abdullahi.weatherapp.data.network.DataSource.WeatherNetworkDataSourceImpl
import com.example.abdullahi.weatherapp.data.network.interceptor.ConnectivityInterceptor
import com.example.abdullahi.weatherapp.data.network.interceptor.ConnectivityInterceptorImpl
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository
import com.example.abdullahi.weatherapp.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    }

}
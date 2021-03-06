package com.example.abdullahi.weatherapp

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.abdullahi.weatherapp.data.WeatherApiService
import com.example.abdullahi.weatherapp.data.db.ForecastDatabase
import com.example.abdullahi.weatherapp.data.network.dataSource.WeatherNetworkDataSource
import com.example.abdullahi.weatherapp.data.network.dataSource.WeatherNetworkDataSourceImpl
import com.example.abdullahi.weatherapp.data.network.interceptor.ConnectivityInterceptor
import com.example.abdullahi.weatherapp.data.network.interceptor.ConnectivityInterceptorImpl
import com.example.abdullahi.weatherapp.data.provider.LocationProvider
import com.example.abdullahi.weatherapp.data.provider.LocationProviderImpl
import com.example.abdullahi.weatherapp.data.provider.UnitProvider
import com.example.abdullahi.weatherapp.data.provider.UnitProviderImpl
import com.example.abdullahi.weatherapp.data.repository.ForecastRepository
import com.example.abdullahi.weatherapp.data.repository.ForecastRepositoryImpl
import com.example.abdullahi.weatherapp.ui.fragments.weather.current.CurrentWeatherViewModelFactory
import com.example.abdullahi.weatherapp.ui.fragments.weather.future.list.FutureWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : MultiDexApplication(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(),instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureWeatherViewModelFactory(instance(), instance()) }
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}
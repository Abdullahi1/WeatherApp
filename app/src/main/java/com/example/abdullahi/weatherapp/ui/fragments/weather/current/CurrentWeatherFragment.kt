package com.example.abdullahi.weatherapp.ui.fragments.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.abdullahi.weatherapp.R
import com.example.abdullahi.weatherapp.data.WeatherApiService
import com.example.abdullahi.weatherapp.data.network.DataSource.WeatherNetworkDataSourceImpl
import com.example.abdullahi.weatherapp.data.network.interceptor.ConnectivityInterceptorImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        val apiService = WeatherApiService(
            ConnectivityInterceptorImpl(
                this.context!!
            )
        )

        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)

        weatherNetworkDataSource.downloadCurrentWeather.observe(this, Observer {weatherResponse ->
            current_text_view.text = weatherResponse.toString()
        })

        GlobalScope.launch(Dispatchers.Main){
//            val currentWeatherResponse = apiService.getCurrentWeather(location = "New York",tempUnit = "m").await()
//            current_text_view.text = currentWeatherResponse.toString()
            weatherNetworkDataSource.fetchCurrentWeather("New York", "m")
        }
    }

}

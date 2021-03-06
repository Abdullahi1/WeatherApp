package com.example.abdullahi.weatherapp.ui.fragments.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.abdullahi.weatherapp.R
import com.example.abdullahi.weatherapp.internal.glide.GlideApp
import com.example.abdullahi.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

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
        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel
        bindUI()

//        val apiService = WeatherApiService(
//            ConnectivityInterceptorImpl(
//                this.context!!
//            )
//        )
//
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downloadCurrentWeather.observe(this, Observer {weatherResponse ->
//            current_text_view.text = weatherResponse.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main){
//            val currentWeatherResponse = apiService.getCurrentWeather(weatherLocation = "New York",tempUnit = "m").await()
//            current_text_view.text = currentWeatherResponse.toString()
//            weatherNetworkDataSource.fetchCurrentWeather("New York", "m")
//        }
//    }

    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()

        val currentWeatherLocation = viewModel.weatherLocation.await()

        currentWeatherLocation.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
            updateLocation(it.name, it.country)

        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToday()
            updateTemperature(it.temperature,it.feelslike)
            updateCondition(it.weatherDescriptions[0])
            updatePrecipitation(it.precip)
            updateWind(it.windDir,it.windSpeed)
            updateVisibility(it.visibility)
            updateLastUpdated(it.observationTime)

            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons[0])
                .into(imageView_condition_icon)
        })


    }

    private fun updateLastUpdated(observationTime: String) {
        textView_last_updated.text = "Last updated : $observationTime"
    }


    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) metric else imperial
    }

    private fun updateLocation(locationName: String, countryName:String){
      (activity as? AppCompatActivity)?.supportActionBar?.title = "$locationName ($countryName)"
    }

    private fun updateDateToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature:Double, feelsLike: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C" ,"°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateCondition(condition: String){
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm" ,"in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateVisibility(visibility: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        textView_visibility.text = "Visibility: $visibility $unitAbbreviation"
    }

    private fun updateWind(windDir: String, windSpeed: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDir, $windSpeed $unitAbbreviation"
    }


}


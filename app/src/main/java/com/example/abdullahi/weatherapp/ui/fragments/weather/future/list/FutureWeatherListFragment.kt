package com.example.abdullahi.weatherapp.ui.fragments.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.example.abdullahi.weatherapp.R
import com.example.abdullahi.weatherapp.ui.base.ScopedFragment
import com.example.abdullahi.weatherapp.ui.fragments.weather.current.CurrentWeatherViewModel
import com.example.abdullahi.weatherapp.ui.fragments.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureWeatherListFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: FutureWeatherViewModelFactory by instance()

    companion object {
        fun newInstance() =
            FutureWeatherListFragment()
    }

    private lateinit var viewModel: FutureWeatherListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_weather_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(FutureWeatherListViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FutureWeatherListViewModel::class.java)
    }

}

package com.example.abdullahi.weatherapp.ui.fragments.weather.future.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.abdullahi.weatherapp.R
import com.example.abdullahi.weatherapp.ui.base.ScopedFragment

class FutureWeatherListFragment : ScopedFragment() {

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
        viewModel = ViewModelProviders.of(this).get(FutureWeatherListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

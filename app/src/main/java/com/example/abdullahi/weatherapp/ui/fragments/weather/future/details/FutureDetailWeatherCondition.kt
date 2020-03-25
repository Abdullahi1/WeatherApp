package com.example.abdullahi.weatherapp.ui.fragments.weather.future.details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.abdullahi.weatherapp.R

class FutureDetailWeatherCondition : Fragment() {

    companion object {
        fun newInstance() = FutureDetailWeatherCondition()
    }

    private lateinit var viewModel: FutureDetailWeatherConditionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_condition_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this).get(FutureDetailWeatherConditionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

package com.example.abdullahi.weatherapp.data

import com.example.abdullahi.weatherapp.data.network.interceptor.ConnectivityInterceptor
import com.example.abdullahi.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.abdullahi.weatherapp.future.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "81112213acc8c46b42788e0022b6f1ee"
const val BASE_URL = "http://api.weatherstack.com/"
//URL: http://api.weatherstack.com/current?access_key=81112213acc8c46b42788e0022b6f1ee&query=New%20York
//Optional
    //language= en
    //units = {[m for Metric],[ s for Scientific ], [f for Fahrenheit]}

interface WeatherApiService {

    @GET("current")
    fun getCurrentWeatherAsync(
        @Query("query") location : String,
        @Query("units") tempUnit: String
    ):Deferred<CurrentWeatherResponse>

    @GET("forecast")
    fun getFutureWeatherAsync(
        @Query("query") location : String,
        @Query("units") tempUnit: String,
        @Query("forecast_days") forecastDays : String,
        @Query("hourly") hourly: String
    ):Deferred<FutureWeatherResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor : ConnectivityInterceptor
        ):WeatherApiService{
            val requestInterceptor = Interceptor{

                //Creating the interceptor to be added on every request
                val url  = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()


                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor it.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}
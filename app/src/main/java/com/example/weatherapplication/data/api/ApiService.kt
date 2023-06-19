package com.example.weatherapplication.data.api

import com.example.weatherapplication.data.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service class that holds API Path for Weather Data.
 */
interface ApiService {

    @GET("data/2.5/weather/")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): WeatherData

    @GET("data/2.5/weather/")
    suspend fun getWeatherWithLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): WeatherData
}

package com.example.weatherapplication.data.repository

import com.example.weatherapplication.data.api.ApiService
import com.example.weatherapplication.data.model.WeatherData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository used to provide the Weather Data
 */
@Singleton
open class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getWeatherData(
        city: String, units: String, appId: String
    ): WeatherData {
        return withContext(IO) {
            apiService.getWeather(
                city = city, units = units, appId = appId
            )
        }
    }

    suspend fun getWeatherDataWithLocation(
        lat: Double, lon: Double, units: String, appId: String
    ): WeatherData {
        return withContext(IO) {
            apiService.getWeatherWithLocation(
                lat = lat, lon = lon, units = units, appId = appId
            )
        }
    }
}
package com.example.weatherapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.model.WeatherData
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Viewmodel to fetch the weather Data and post it to the UI.
 */
class WeatherViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<WeatherState>(value = WeatherState.Loading(true))
    val uiState: StateFlow<WeatherState> = _uiState.asStateFlow()

    var userLatitude by mutableStateOf(0.0)
    var userLongitude by mutableStateOf(0.0)

    var userEnteredLocation by mutableStateOf("")

    /**
     * This function is called to fetch weather data when user enters the city and click on submit button.
     */
    fun fetchWeatherData() {
        viewModelScope.launch {
            _uiState.emit(WeatherState.Loading(true))
            try {
                val result = weatherRepository.getWeatherData(
                        city = userEnteredLocation,
                        units = AppConstant.UNITS,
                        appId = AppConstant.API_KEY
                    )
                _uiState.emit(WeatherState.Loading(false))
                _uiState.emit(WeatherState.Success(response = result))

            } catch (e: Exception) {
                _uiState.emit(WeatherState.Error)
            }
        }
    }

    /**
     * This function is called to fetch the weather data when users enables the location.
     */
    fun fetchWeatherDataWhenLocationEnabled() {
        viewModelScope.launch {
            try {
                val result = weatherRepository.getWeatherDataWithLocation(
                    lat = userLatitude,
                    lon = userLongitude,
                    units = AppConstant.UNITS,
                    appId = AppConstant.API_KEY
                )
                _uiState.emit(WeatherState.Loading(false))
                _uiState.emit(WeatherState.Success(response = result))
            } catch (e: Exception) {
                _uiState.emit(WeatherState.Error)
            }
        }
    }

    /**
     * This function is used to handle the user input.
     */
    fun onUserEnteredLocation(location: String) {
        userEnteredLocation = location
    }

}

sealed class WeatherState {
    data class Success(val response: WeatherData) : WeatherState()
    data class Loading(val isLoading: Boolean): WeatherState()
    object Error : WeatherState()
}


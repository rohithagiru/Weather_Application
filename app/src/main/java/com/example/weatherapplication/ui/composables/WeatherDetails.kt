package com.example.weatherapplication.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.weatherapplication.ui.WeatherState
import com.example.weatherapplication.ui.WeatherViewModel

/**
 * Composable for displaying the weather details screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetails(
    weatherViewModel: WeatherViewModel
) {
    val result = weatherViewModel.uiState.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (result) {
                is WeatherState.Success -> {
                    DataRow(
                        title = "Min Temp",
                        text = result.response.main.temp_min.toString() + " °F"
                    )
                    DataRow(
                        title = "Max Temp",
                        text = result.response.main.temp_max.toString() + " °F"
                    )
                    DataRow(title = "Wind", text = result.response.wind.speed.toString() + " mph")
                    DataRow(
                        title = "Humidity",
                        text = result.response.main.humidity.toString() + "%"
                    )
                }

                is WeatherState.Error -> {}

                is WeatherState.Loading -> {}
            }
        }
    }
}
package com.example.weatherapplication.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapplication.ui.WeatherState
import com.example.weatherapplication.ui.WeatherViewModel

/**
 * Composable for displaying the weather home screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherUiScreen(
    weatherViewModel: WeatherViewModel,
    onNavigateToDetails: () -> Unit
) {
    val result = weatherViewModel.uiState.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(value = weatherViewModel.userEnteredLocation,
                    onValueChange = { newEntryFromUser ->
                        weatherViewModel.onUserEnteredLocation(newEntryFromUser)
                    },
                    label = { Text(text = "Enter City for Weather Updates") })
            }
            Row(
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top
            ) {
                Button(onClick = {
                    weatherViewModel.fetchWeatherData()
                }) {
                    Text(text = "Submit")
                }
            }


            when (result) {
                is WeatherState.Success -> {
                    DataRow(
                        title = "Current Temperature",
                        text = result.response.main.temp.toString() + " °F",
                        iconCode = result.response.weather[0].icon
                    )
                    Button(onClick = onNavigateToDetails) {
                        Text(text = "Click here for more Details")
                    }
                }

                is WeatherState.Error -> {
                    Row {
                        Text(
                            text = "Please Enter Correct City Name",
                            modifier = Modifier,
                            fontSize = 20.sp
                        )
                    }
                }

                is WeatherState.Loading -> {
                    CircularProgressIndicator()
                }
            }

        }
    }
}

@Composable
fun DataRow(title: String? = null, text: String, iconCode: String? = null) {
    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            title?.let {
                Text(
                    text = it, modifier = Modifier, fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            iconCode?.let {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${iconCode}.png",
                    modifier = Modifier.size(70.dp),
                    contentDescription = ""
                )
            }
            Text(
                text = text, modifier = Modifier, fontSize = 20.sp
            )
        }

    }
}
package com.example.weatherapplication.ui.composables

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapplication.ui.WeatherViewModel

/**
 * Navigation component to manage the navigation between the composables.
 */
@Composable
fun WeatherNavHost(
    weatherViewModel: WeatherViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            WeatherUiScreen(weatherViewModel = weatherViewModel,
                onNavigateToDetails = { navController.navigate("details") })
        }
        composable("details") { WeatherDetails(weatherViewModel) }
    }
}
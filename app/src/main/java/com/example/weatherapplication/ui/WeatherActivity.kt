package com.example.weatherapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapplication.WeatherApplication
import com.example.weatherapplication.di.component.DaggerActivityComponent
import com.example.weatherapplication.di.module.ActivityModule
import com.example.weatherapplication.ui.composables.WeatherNavHost
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject


class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val permissionId = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerActivityComponent.builder()
            .applicationComponent((application as WeatherApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLocation()

        setContent {
            WeatherNavHost(weatherViewModel = weatherViewModel)
        }
    }
    
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        weatherViewModel.userLatitude = location.latitude
                        weatherViewModel.userLongitude = location.longitude
                        weatherViewModel.fetchWeatherDataWhenLocationEnabled()
                    }
                }
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
}


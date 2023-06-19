package com.example.weatherapplication.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.di.ActivityContext
import com.example.weatherapplication.ui.WeatherViewModel
import com.example.weatherapplication.ui.base.ViewModelProviderFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideWeatherViewModel(weatherRepository: WeatherRepository): WeatherViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(WeatherViewModel::class) {
                WeatherViewModel(weatherRepository)
            })[WeatherViewModel::class.java]
    }

}
package com.example.weatherapplication.di.component

import android.content.Context
import com.example.weatherapplication.WeatherApplication
import com.example.weatherapplication.data.api.ApiService
import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.di.ApplicationContext
import com.example.weatherapplication.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: WeatherApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getApiService(): ApiService

    fun getWeatherRepository(): WeatherRepository
}
package com.example.weatherapplication.di.module

import android.content.Context
import com.example.weatherapplication.WeatherApplication
import com.example.weatherapplication.data.api.ApiService
import com.example.weatherapplication.di.ApplicationContext
import com.example.weatherapplication.di.BaseUrl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val application: WeatherApplication
) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://api.openweathermap.org/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideApiService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ApiService::class.java)
    }
}
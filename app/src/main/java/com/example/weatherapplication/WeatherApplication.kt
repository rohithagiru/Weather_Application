package com.example.weatherapplication

import android.app.Application
import com.example.weatherapplication.di.component.ApplicationComponent
import com.example.weatherapplication.di.component.DaggerApplicationComponent
import com.example.weatherapplication.di.module.ApplicationModule

class WeatherApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}
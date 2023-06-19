package com.example.weatherapplication.di.component

import com.example.weatherapplication.di.ActivityScope
import com.example.weatherapplication.di.module.ActivityModule
import com.example.weatherapplication.ui.WeatherActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: WeatherActivity)

}
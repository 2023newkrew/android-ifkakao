package com.example.ifkakao.di

import android.app.Application
import com.example.ifkakao.di.component.ApplicationComponent
import com.example.ifkakao.di.component.DaggerApplicationComponent

class MyApplication : Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}
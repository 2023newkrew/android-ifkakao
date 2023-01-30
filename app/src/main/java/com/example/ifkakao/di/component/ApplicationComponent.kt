package com.example.ifkakao.di.component

import com.example.ifkakao.di.module.MainModule
import com.example.ifkakao.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun mainComponent(): MainComponent.Factory
}
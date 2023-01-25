package com.example.ifkakao.di

import com.example.ifkakao.data.source.remote.WorldTimeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideWorldTimeApi(): WorldTimeApi = WorldTimeApi.create()
}
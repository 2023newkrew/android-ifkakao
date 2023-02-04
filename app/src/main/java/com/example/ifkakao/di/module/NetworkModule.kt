package com.example.ifkakao.di.module

import com.example.ifkakao.data.network.SessionApiService
import com.example.ifkakao.di.scope.ActivityScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @ActivityScope
    @Provides
    fun provideSessionApiService(): SessionApiService {
        val BASE_URL = "http://104.198.248.76:3000/"

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SessionApiService::class.java)
    }
}
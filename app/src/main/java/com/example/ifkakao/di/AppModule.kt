package com.example.ifkakao.di

import com.example.ifkakao.data.data_source.service.SessionService
import com.example.ifkakao.data.repository.SessionRepositoryImpl
import com.example.ifkakao.domain.repository.SessionRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

const val URL_BASE = "http://104.198.248.76:3000"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionService(): SessionService {
        @OptIn(ExperimentalSerializationApi::class)
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideSessionRepository(service: SessionService): SessionRepository{
        return SessionRepositoryImpl(service)
    }
}
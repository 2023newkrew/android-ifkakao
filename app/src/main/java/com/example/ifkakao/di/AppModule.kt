package com.example.ifkakao.di

import com.example.ifkakao.data.repository.SessionRepositoryMock
import com.example.ifkakao.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionRepository(): SessionRepository {
        return SessionRepositoryMock()
    }
}
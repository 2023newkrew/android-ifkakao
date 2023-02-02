package com.example.ifkakao.data.di

import com.example.ifkakao.data.repository.DataStoreRepositoryImpl
import com.example.ifkakao.data.repository.SessionRepositoryImpl
import com.example.ifkakao.domain.repository.DataStoreRepository
import com.example.ifkakao.domain.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        noteRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository
}
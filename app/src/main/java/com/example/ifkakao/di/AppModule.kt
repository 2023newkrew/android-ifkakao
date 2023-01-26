package com.example.ifkakao.di

import android.app.Application
import androidx.room.Room
import com.example.ifkakao.data.data_source.local.SessionLikeDatabase
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
    fun provideSessionLikeDatabase(app: Application): SessionLikeDatabase {
        return Room.databaseBuilder(
            app,
            SessionLikeDatabase::class.java,
            SessionLikeDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideSessionRepository(sessionLikeDatabase: SessionLikeDatabase): SessionRepository {
        return SessionRepositoryMock(sessionLikeDatabase.sessionLikeDao)
    }
}
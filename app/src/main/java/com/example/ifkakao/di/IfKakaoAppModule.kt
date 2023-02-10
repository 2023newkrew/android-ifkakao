package com.example.ifkakao.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ifkakao.data.data_source.remote.SessionService
import com.example.ifkakao.data.repository.LocalRepositoryImpl
import com.example.ifkakao.data.repository.RemoteRepositoryImpl
import com.example.ifkakao.domain.repository.LocalRepository
import com.example.ifkakao.domain.repository.RemoteRepository
import com.example.ifkakao.domain.usecase.LoadLikesUseCase
import com.example.ifkakao.domain.usecase.LoadSessionsUseCase
import com.example.ifkakao.domain.usecase.SaveLikeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IfKakaoAppModule {

    @Provides
    @Singleton
    fun provideSessionService(): SessionService {
        return SessionService.create()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(service: SessionService): RemoteRepository {
        return RemoteRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideLoadSessionsUseCase(repository: RemoteRepository): LoadSessionsUseCase {
        return LoadSessionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("if_kakao_shared_pref", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideLocalRepository(sharedPreferences: SharedPreferences): LocalRepository {
        return LocalRepositoryImpl(sharedPreferences)
    }


    @Provides
    @Singleton
    fun provideLoadLikesUseCase(repository: LocalRepository): LoadLikesUseCase {
        return LoadLikesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveLikesUseCase(repository: LocalRepository): SaveLikeUseCase {
        return SaveLikeUseCase(repository)
    }

}
package com.example.ifkakao.di

import android.content.Context
import androidx.room.Room
import com.example.ifkakao.data.data_source.dao.LikeDao
import com.example.ifkakao.data.data_source.database.LikeDatabase
import com.example.ifkakao.data.data_source.service.SessionService
import com.example.ifkakao.data.repository.LikeRepositoryImpl
import com.example.ifkakao.data.repository.SessionRepositoryImpl
import com.example.ifkakao.domain.repository.LikeRepository
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.domain.usecase.DeleteLikeUseCase
import com.example.ifkakao.domain.usecase.GetLikeListUseCase
import com.example.ifkakao.domain.usecase.GetSessionInfoListUseCase
import com.example.ifkakao.domain.usecase.InsertLikeUseCase
import com.example.ifkakao.domain.usecase.bundle.LikeUseCaseBundle
import com.example.ifkakao.util.TABLE_NAME
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
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
    fun provideLikeDatabase(@ApplicationContext context: Context): LikeDatabase {
        return Room.databaseBuilder(
            context,
            LikeDatabase::class.java,
            TABLE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLikeDao(database: LikeDatabase): LikeDao {
        return database.likeDao()
    }

    @Provides
    @Singleton
    fun provideLikeRepository(dao: LikeDao): LikeRepository {
        return LikeRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideLikeUseCaseBundle(repository: LikeRepository): LikeUseCaseBundle {
        return LikeUseCaseBundle(
            GetLikeListUseCase(repository),
            InsertLikeUseCase(repository),
            DeleteLikeUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideGetSessionInfoListUseCase(repository: SessionRepository): GetSessionInfoListUseCase {
        return GetSessionInfoListUseCase(repository)
    }

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
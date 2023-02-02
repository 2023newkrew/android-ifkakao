package com.example.ifkakao.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ifkakao.BASE_URL_SESSIONS
import com.example.ifkakao.DATA_STORE_NAME_LIKE
import com.example.ifkakao.data.retrofit.SessionService
import com.example.ifkakao.domain.repository.DataStoreRepository
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.domain.use_case.GetLikeUseCase
import com.example.ifkakao.domain.use_case.GetSessionsUseCase
import com.example.ifkakao.domain.use_case.PutLikeUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME_LIKE)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideSessionService(): SessionService =
        Retrofit.Builder()
            .baseUrl(BASE_URL_SESSIONS)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create()

    @Provides
    @Singleton
    fun provideGetSessionsUseCase(repository: SessionRepository) = GetSessionsUseCase(repository)

    @Provides
    @Singleton
    fun provideLikeDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideGetLikeUseCase(repository: DataStoreRepository) = GetLikeUseCase(repository)

    @Provides
    @Singleton
    fun providePutLikeUseCase(repository: DataStoreRepository) = PutLikeUseCase(repository)
}
package com.example.ifkakao.presentation.session

import com.example.ifkakao.BASE_URL_SESSIONS
import com.example.ifkakao.TYPE_KEY_KEYNOTE
import com.example.ifkakao.data.repository.SessionRepositoryImpl
import com.example.ifkakao.data.retrofit.SessionService
import com.example.ifkakao.domain.use_case.GetSessionsUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create

class SessionViewModelTest {
    @OptIn(ExperimentalSerializationApi::class)
    private val sessionService: SessionService =
        Retrofit.Builder()
            .baseUrl(BASE_URL_SESSIONS)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create()
    private val sessionRepositoryImpl = SessionRepositoryImpl(sessionService)
    private val sessionsUseCase = GetSessionsUseCase(sessionRepositoryImpl)
    private val viewModel = SessionViewModel(sessionsUseCase)

    @Test
    fun testFilterInfoList() = runBlocking {
        viewModel.loadInfoList()
        delay(5000)
        viewModel.filterInfoListByType(TYPE_KEY_KEYNOTE)
        Assert.assertEquals(viewModel.state.value.filteredInfoList[0].sessionType, TYPE_KEY_KEYNOTE)
    }
}
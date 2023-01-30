package com.example.ifkakao.data.repository

import com.example.ifkakao.BASE_URL_SESSIONS
import com.example.ifkakao.data.data_source.mapper.toInfo
import com.example.ifkakao.data.retrofit.SessionService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create

class SessionRepositoryTest {
    @OptIn(ExperimentalSerializationApi::class)
    private val sessionService: SessionService =
        Retrofit.Builder()
            .baseUrl(BASE_URL_SESSIONS)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create()
    private val sessionRepositoryImpl = SessionRepositoryImpl(sessionService)

    @Test
    fun testGetSessions() = runBlocking {
        val sessions = sessionRepositoryImpl.getSessions()
        val infoList = sessions.map { it.toInfo() }
        Assert.assertEquals(infoList[0].id, 110)
    }
}
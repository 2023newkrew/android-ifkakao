package com.example.ifkakao.data.data_source.remote

import com.example.ifkakao.data.data_source.remote.dto.SessionDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SessionApi {
    @GET("ifKakao")
    suspend fun getAllSessions(
        @Query("accept") accept: String = "application/json",
    ): List<SessionDTO>

    companion object {
        private const val BASE_URL = "http://104.198.248.76:3000/"

        fun create(): SessionApi {
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
                .create(SessionApi::class.java)
        }
    }
}
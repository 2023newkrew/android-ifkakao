package com.example.ifkakao.data.data_source.remote

import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface SessionService {

    @GET("ifKakao")
    suspend fun getIfKakaoSessions(
        @Query("accept") accept: String = "application/json",
    ): List<ResultSession>

    companion object {
        private const val BASE_URL = "http://104.198.248.76:3000/"

        fun create(): SessionService {
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
                .create(SessionService::class.java)
        }
    }
}
package com.example.ifkakao.data.source.remote

import com.example.ifkakao.domain.model.Timezone
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

@OptIn(ExperimentalSerializationApi::class)
interface WorldTimeApi {

    companion object {
        private const val baseUrl = "http://worldtimeapi.org/api/"

        fun create(): WorldTimeApi =
            Retrofit.Builder()
                .baseUrl(WorldTimeApi.baseUrl)
                .addConverterFactory(Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(MediaType.parse("application/json")!!))
                .build()
                .create()
    }

    @GET("timezone/{city}")
    suspend fun getWorldTime(@Path("city") city: String = "Asia/Tokyo"): Timezone
}
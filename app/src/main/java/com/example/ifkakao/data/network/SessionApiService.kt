package com.example.ifkakao.data.network

import com.example.ifkakao.data.dto.ResultSession
import retrofit2.http.GET
import retrofit2.http.Query

interface SessionApiService {
    @GET("ifKakao")
    suspend fun getSessions(
        @Query("accept") accept: String = "application/json",
    ): List<ResultSession>
}
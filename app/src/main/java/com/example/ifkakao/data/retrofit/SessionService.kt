package com.example.ifkakao.data.retrofit

import com.example.ifkakao.data.data_source.dto.Sessions
import retrofit2.http.GET

interface SessionService {
    @GET("sessions")
    suspend fun getSessions(): Sessions
}
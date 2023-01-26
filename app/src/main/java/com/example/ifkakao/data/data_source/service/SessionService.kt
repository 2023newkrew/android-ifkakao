package com.example.ifkakao.data.data_source.service

import com.example.ifkakao.data.data_source.dto.Session
import retrofit2.http.GET

interface SessionService {
    @GET("ifKakao")
    suspend fun getSessions(): List<Session>
}
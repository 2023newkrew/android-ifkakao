package com.example.ifkakao.data.repository

import com.example.ifkakao.data.dto.ResultSession
import com.example.ifkakao.data.network.SessionApiService
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(){
    @Inject
    lateinit var sessionApiService: SessionApiService

    suspend fun getSessions(): List<ResultSession>{
        return sessionApiService.getSessions()
    }
}
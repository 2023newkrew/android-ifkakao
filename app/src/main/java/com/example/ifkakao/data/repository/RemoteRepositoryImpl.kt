package com.example.ifkakao.data.repository

import com.example.ifkakao.data.data_source.remote.SessionService
import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import com.example.ifkakao.domain.repository.RemoteRepository

class RemoteRepositoryImpl(private val sessionService: SessionService) : RemoteRepository {

    override suspend fun loadSessions(): List<ResultSession> {
        return try {
            sessionService.getIfKakaoSessions()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
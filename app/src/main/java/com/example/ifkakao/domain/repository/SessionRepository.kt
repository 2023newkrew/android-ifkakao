package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.dto.ResultSession
import com.example.ifkakao.data.repository.SessionRepositoryImpl
import javax.inject.Inject

class SessionRepository @Inject constructor(private var sessionRepositoryImpl: SessionRepositoryImpl) {
    suspend fun getSessions(): List<ResultSession> {
        return sessionRepositoryImpl.getSessions()
    }
}
package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.repository.SessionRepositoryImpl
import javax.inject.Inject

class SessionRepository @Inject constructor(private var sessionRepositoryImpl: SessionRepositoryImpl) {
    suspend fun getSessions() = sessionRepositoryImpl.getSessions()
}
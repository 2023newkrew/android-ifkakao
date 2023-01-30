package com.example.ifkakao.data.repository

import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.data.retrofit.SessionService
import com.example.ifkakao.domain.repository.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(private val sessionService: SessionService) :
    SessionRepository {
    override suspend fun getSessions(): List<Session> = sessionService.getSessions()
}
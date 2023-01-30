package com.example.ifkakao.domain.use_case

import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.repository.SessionRepository

class GetSessionsUseCase(private val repository: SessionRepository) {
    suspend operator fun invoke(): List<Session> = repository.getSessions()
}
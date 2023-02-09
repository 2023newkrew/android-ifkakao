package com.example.ifkakao.domain.usecase

import com.example.ifkakao.data.data_source.remote.mapper.toSession
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.repository.RemoteRepository

class LoadSessionsUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke(): List<Session> {
        return repository.loadSessions().map { it.toSession() }
    }
}
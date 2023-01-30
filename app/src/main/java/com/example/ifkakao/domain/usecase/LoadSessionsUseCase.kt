package com.example.ifkakao.domain.usecase

import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import com.example.ifkakao.domain.repository.RemoteRepository

class LoadSessionsUseCase(private val repository: RemoteRepository) {
    suspend operator fun invoke(): List<ResultSession> {
        return repository.loadSessions()
    }
}
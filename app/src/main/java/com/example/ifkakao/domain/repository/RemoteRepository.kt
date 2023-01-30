package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.data_source.remote.dto.ResultSession

interface RemoteRepository {
    suspend fun loadSessions(): List<ResultSession>
}
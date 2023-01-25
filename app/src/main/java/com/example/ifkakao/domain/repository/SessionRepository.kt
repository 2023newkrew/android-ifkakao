package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.data_source.dto.Sessions

interface SessionRepository {
    suspend fun getSessions(): Sessions
}
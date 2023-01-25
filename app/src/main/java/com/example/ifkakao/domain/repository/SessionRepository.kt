package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.data_source.dto.Session

interface SessionRepository {
    suspend fun getSessions(): List<Session>
}
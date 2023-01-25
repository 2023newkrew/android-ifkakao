package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.data_source.remote.SessionApi
import com.example.ifkakao.domain.model.Session

interface SessionRepository {
    suspend fun getAllSessions(): List<Session>
}
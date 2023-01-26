package com.example.ifkakao.domain.repository

import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.model.SessionInfo
import com.example.ifkakao.util.ApiResult

interface SessionRepository {
    suspend fun getSessions(): ApiResult<List<SessionInfo>>
}
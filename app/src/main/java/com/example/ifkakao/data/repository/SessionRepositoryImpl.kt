package com.example.ifkakao.data.repository

import com.example.ifkakao.data.data_source.mapper.toSessionInfo
import com.example.ifkakao.data.data_source.service.SessionService
import com.example.ifkakao.domain.model.SessionInfo
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.util.ApiError
import com.example.ifkakao.util.ApiException
import com.example.ifkakao.util.ApiResult
import com.example.ifkakao.util.ApiSuccess
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val service: SessionService
):SessionRepository {
    override suspend fun getSessions(): ApiResult<List<SessionInfo>> {
        return try {
            val response = service.getSessions()
            if (response.isSuccessful) {
                val sessions = response.body()?.map { it.toSessionInfo() }?: emptyList()
                ApiSuccess(sessions)
            } else {
                ApiError(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiException(e)
        }
    }
}
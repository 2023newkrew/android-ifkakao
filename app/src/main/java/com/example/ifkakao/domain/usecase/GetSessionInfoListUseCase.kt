package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.model.*
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.util.ApiError
import com.example.ifkakao.util.ApiException
import com.example.ifkakao.util.ApiResult
import com.example.ifkakao.util.ApiSuccess

class GetSessionInfoListUseCase(private val repository: SessionRepository) {
    companion object {
        private var result: ApiResult<List<SessionInfo>>? = null
    }

    suspend operator fun invoke(
        likeList: List<Like>,
        types: Set<SessionType>? = null,
        track: Set<Track>? = null,
        company: Set<Company>? = null,
    ): ApiResult<List<SessionInfo>>? {
        if (result == null) {
            result = repository.getSessions()
        }
        if (result is ApiError || result is ApiException) {
            result = repository.getSessions()
        }

        val likeSessionList = likeList.map { it.sessionId }

        return when (result) {
            is ApiSuccess -> {
                (result as ApiSuccess<List<SessionInfo>>).data.map {
                    if (it.id in likeSessionList) {
                        it.isLiked = true
                    }
                }
                result
            }
            else -> {
                result
            }
        }
    }
}
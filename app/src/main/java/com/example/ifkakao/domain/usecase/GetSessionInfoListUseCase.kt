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
        types: Set<SessionType> = setOf(),
        tracks: Set<Track> = setOf(),
        companies: Set<Company> = setOf(),
        day: SessionDay = SessionDay.Null,
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
                val tempSessionInfoList = (result as ApiSuccess<List<SessionInfo>>).data.map {
                    it.copy(
                        isLiked = it.id in likeSessionList
                    )
                }
                val apiSuccess = ApiSuccess<List<SessionInfo>>(
                    data = tempSessionInfoList.filter {
                        var temp = true
                        if (types.isNotEmpty()) {
                            temp = it.sessionType in types
                        }
                        if (tracks.isNotEmpty()) {
                            temp = if (it.track.toSet().intersect(tracks).isNotEmpty()) {
                                temp
                            } else {
                                false
                            }
                        }
                        if (companies.isNotEmpty()) {
                            temp = temp && it.company in companies
                        }
                        if (day != SessionDay.Null) {
                            temp = temp && it.sessionDay == day
                        }
                        temp
                    }
                )
                apiSuccess
            }
            else -> {
                result
            }
        }
    }
}
package com.example.ifkakao.domain.use_case

import com.example.ifkakao.domain.model.SessionInfo
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.util.ApiResult
import javax.inject.Inject

class GetSessionInfoListUseCase @Inject constructor(private val repository: SessionRepository) {

    suspend operator fun invoke():ApiResult<List<SessionInfo>> {
        return repository.getSessions()
    }
}
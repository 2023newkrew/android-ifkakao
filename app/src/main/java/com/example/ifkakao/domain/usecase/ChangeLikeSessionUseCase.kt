package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.repository.SessionRepository

class ChangeLikeSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(session: Session, isLike: Boolean) {
        if (isLike) {
            sessionRepository.likeSession(session)
        } else {
            sessionRepository.unlikeSession(session)
        }
    }
}
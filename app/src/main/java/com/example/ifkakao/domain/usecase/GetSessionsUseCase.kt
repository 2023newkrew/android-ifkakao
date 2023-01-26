package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.presentation.session_list.SessionFilter

class GetSessionsUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(
        sessionFilter: SessionFilter, showLikeOnly: Boolean
    ): List<Session> {
        var ret = sessionRepository.getAllSessions().asSequence()
        if (!sessionFilter.isEnable) return ret.toList()
        if (sessionFilter.typeFilterEnable) ret =
            ret.filter { it.sessionType in sessionFilter.sessionTypes }
        if (sessionFilter.companyFilterEnable) ret =
            ret.filter { it.company in sessionFilter.companies }
        if (sessionFilter.trackFilterEnable) ret =
            ret.filter { it.tracks.intersect(sessionFilter.tracks.toSet()).isNotEmpty() }
        if (showLikeOnly) ret = ret.filter { it.isLike }

        return ret.toList()
    }
}
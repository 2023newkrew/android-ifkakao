package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.presentation.session_list.SessionFilter

class GetSessionsUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(
        sessionDay: Int,
        sessionFilter: SessionFilter,
        showLikeOnly: Boolean
    ): List<Session> {
        var ret = sessionRepository.getAllSessions().asSequence()

        // like-filter
        if (showLikeOnly) ret = ret.filter { it.isLike }

        // day-filter
        if (sessionDay != 0) ret = ret.filter { it.sessionDay == sessionDay }

        // filter
        if (!sessionFilter.isEnable) return ret.sortedBy { it.id }.toList()
        if (sessionFilter.typeFilterEnable) ret =
            ret.filter { it.sessionType in sessionFilter.sessionTypes }
        if (sessionFilter.companyFilterEnable) ret =
            ret.filter { it.company in sessionFilter.companies }
        if (sessionFilter.trackFilterEnable) ret =
            ret.filter { it.tracks.intersect(sessionFilter.tracks.toSet()).isNotEmpty() }



        return ret.sortedBy { it.id }.toList()
    }
}
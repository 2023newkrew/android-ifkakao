package com.example.ifkakao.presentation.session_list

import com.example.ifkakao.domain.model.Session

sealed class SessionListEvent {
    data class LikeSession(val session: Session) : SessionListEvent()
    data class UnLikeSession(val session: Session) : SessionListEvent()
    data class ChangeSessionDay(val sessionDay: Int) : SessionListEvent()
    data class FilterSessions(val sessionFilter: SessionFilter) : SessionListEvent()
    data class ShowLikeSessionsOnly(val isLikeSessionsOnly: Boolean) : SessionListEvent()
    object FilterInitialize : SessionListEvent()
}

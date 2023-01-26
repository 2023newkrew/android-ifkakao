package com.example.ifkakao.presentation.session_list

import com.example.ifkakao.domain.model.Session

sealed class SessionListEvent {
    data class Like(val session: Session) : SessionListEvent()
    data class UnLike(val session: Session) : SessionListEvent()
    data class Filter(val sessionFilter: SessionFilter) : SessionListEvent()
    object FilterInitialize : SessionListEvent()
}

package com.example.ifkakao.presentation.session_detail

import com.example.ifkakao.domain.model.Session

sealed class SessionDetailEvent {
    data class LikeSession(val session: Session) : SessionDetailEvent()
    data class UnLikeSession(val session: Session) : SessionDetailEvent()
}

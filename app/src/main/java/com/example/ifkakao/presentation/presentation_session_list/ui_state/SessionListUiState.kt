package com.example.ifkakao.presentation.presentation_session_list.ui_state

import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.model.SessionCompany
import com.example.ifkakao.domain.model.SessionTrack
import com.example.ifkakao.domain.model.SessionType

data class SessionListUiState(
    val sessions: List<Session>,
    val selectType: List<SessionType>,
    val selectTrack: List<SessionTrack>,
    val selectCompany: List<SessionCompany>,
)
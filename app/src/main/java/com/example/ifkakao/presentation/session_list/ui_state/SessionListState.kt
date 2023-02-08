package com.example.ifkakao.presentation.session_list.ui_state

import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.model.SessionCompany
import com.example.ifkakao.domain.model.SessionTrack

data class SessionListState(
    val sessions: List<Session>,
 )
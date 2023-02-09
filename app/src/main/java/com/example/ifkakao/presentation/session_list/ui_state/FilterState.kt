package com.example.ifkakao.presentation.session_list.ui_state

import com.example.ifkakao.domain.model.SessionCompany

data class FilterState(
    val selectType: Set<String>,
    val selectTrack: Set<String>,
    val selectCompany: Set<SessionCompany>,
)
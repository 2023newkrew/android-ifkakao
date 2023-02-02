package com.example.ifkakao.presentation.session_list

import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track

data class SessionFilter(
    val sessionTypes: Set<SessionType> = setOf(),
    val tracks: Set<Track> = setOf(),
    val companies: Set<Company> = setOf(),
)

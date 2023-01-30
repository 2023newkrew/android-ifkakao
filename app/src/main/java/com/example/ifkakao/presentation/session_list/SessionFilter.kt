package com.example.ifkakao.presentation.session_list

import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track

data class SessionFilter(
    val sessionTypes: List<SessionType> = listOf(),
    val tracks: List<Track> = listOf(),
    val companies: List<Company> = listOf(),
)

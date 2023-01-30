package com.example.ifkakao.presentation.session_list

import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track

data class SessionFilter(
    val isEnable: Boolean = false,
    val typeFilterEnable: Boolean = false,
    val trackFilterEnable: Boolean = false,
    val companyFilterEnable: Boolean = false,
    val sessionTypes: List<SessionType> = listOf(),
    val tracks: List<Track> = listOf(),
    val companies: List<Company> = listOf(),
)

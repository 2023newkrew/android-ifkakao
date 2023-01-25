package com.example.ifkakao.domain.model

import java.util.*

data class Session(
    val id: Int,
    val title: String,
    val description: String,
    val date: Date,
    val sessionType: SessionType,
    val tracks: List<Track>,
    val company: Company,
    val sessionVodLink: String,
    val vodThumbUrl: String,
    val users: List<User>,
    val tags: String,
    val pptUrl: String,
)

package com.example.ifkakao.domain.model


data class Session(
    val id: Int,
    val title: String,
    val description: String,
    val timeStamp: Long,
    val sessionDay: Int,
    val sessionType: SessionType,
    val tracks: List<Track>,
    val company: Company,
    val sessionVodLink: String,
    val vodThumbUrl: String,
    val users: List<User>,
    val tags: String,
    val pptUrl: String,
    val isLike: Boolean = false,
)

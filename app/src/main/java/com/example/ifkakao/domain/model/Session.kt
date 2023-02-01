package com.example.ifkakao.domain.model

import java.time.Instant
import java.time.LocalDateTime
import java.util.*


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

fun Session.getTypeAndTracksString(): String {
    return "$sessionType " + tracks.joinToString(
        separator = " "
    ) {
        it.toString()
    }
}

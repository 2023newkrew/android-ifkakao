package com.example.ifkakao.data.data_source.remote.mapper

import com.example.ifkakao.data.data_source.remote.dto.SessionDTO
import com.example.ifkakao.domain.model.*

fun parseTrackList(string: String): List<Track> {
    return string.removeSurrounding("[", "]")
        .replace("\\", "")
        .replace("\"", "")
        .split(",")
        .filter { it.isNotEmpty() }
        .map { Track.fromString(it) }
}

fun SessionDTO.toSession(): Session {

    val users = mutableListOf<User>()
    if (this.user1Id.isNotEmpty()) {
        users.add(
            User(
                id = this.user1Id,
                intro = this.user1Intro,
                imageUrl = this.user1ImageUrl
            )
        )
    }

    if (this.user2Id.isNotEmpty()) {
        users.add(
            User(
                id = this.user2Id,
                intro = this.user2Intro,
                imageUrl = this.user2ImageUrl
            )
        )
    }

    if (this.user3Id.isNotEmpty()) {
        users.add(
            User(
                id = this.user3Id,
                intro = this.user3Intro,
                imageUrl = this.user3ImageUrl
            )
        )
    }

    return Session(
        id = this.id,
        title = this.title,
        company = Company.fromString(this.company),
        tracks = parseTrackList(this.tracks),
        sessionVodLink = this.sessionVodLink,
        sessionType = SessionType.fromString(this.sessionType),
        vodThumbUrl = this.voidThumbUrl,
        tags = this.tags,
        timeStamp = this.timeStamp,
        sessionDay = this.sessionDay.toInt(),
        pptUrl = this.pptUrl,
        description = this.description,
        users = users,
    )
}
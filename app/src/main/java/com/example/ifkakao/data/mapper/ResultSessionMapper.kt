package com.example.ifkakao.data.mapper

import com.example.ifkakao.data.dto.ResultSession
import com.example.ifkakao.domain.model.Session

fun ResultSession.toSession(): Session {
    return Session(
        DB_ID = this.id.toString(),
        title = this.title,
        company = this.company,
        type = this.sessionType,
        date = when (this.sessionDay) {
            "1" -> "12.07"
            "2" -> "12.08"
            "3" -> "12.09"
            else -> ""
        },
        videoLink = this.sessionVodLink,
        track = this.tracks.removeSurrounding("[", "]").split(",").map{it.removeSurrounding("\"")},
        description = this.description,
        presenter = this.user1Id,
        presenterDescription = this.user1Intro,
        presenterImageUrl = this.user1ImageUrl,
    )
}
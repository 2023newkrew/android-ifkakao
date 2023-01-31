package com.example.ifkakao.data.data_source.mapper

import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.model.*

fun Session.toSessionInfo(): SessionInfo {
    // track -> track List

    val trackArr = if (track != "[\"\"]" && track != null) {
        var tempTrack = track.replace("[", "")
            .replace("]", "")
            .replace("\"", "")
        tempTrack.split(",").map { Track.from(it) }
    } else {
        mutableListOf()
    }
    val users = arrayListOf<User>()
    if (user1_id != "" && user1_id != null) {
        users.add(
            User(
                id = user1_id,
                intro = user1_intro ?: "",
                img = user1_img ?: "",
            )
        )
    }
    if (user2_id != "" && user2_id != null) {
        users.add(
            User(
                id = user2_id,
                intro = user2_intro ?: "",
                img = user2_img ?: "",
            )
        )
    }
    if (user3_id != "" && user3_id != null) {
        users.add(
            User(
                id = user3_id,
                intro = user3_intro ?: "",
                img = user3_img ?: "",
            )
        )
    }

    return SessionInfo(
        id = id ?: -1,
        users = users,
        title = title ?: "",
        description = description ?: "",
        sessionType = SessionType.from(session_type ?: "null"),
        sessionDay = SessionDay.from(session_day ?: "null"),
        ppt = ppt ?: "",
        track = trackArr,
        sessionDate = session_date ?: -1,
        company = Company.from(company ?: "null"),
        meetupRegisterLink = meetup_register_link ?: "",
        liveImgUrl = live_img_url ?: "",
        liveQnaUrl = live_qna_url ?: "",
        liveChannelUrl = live_channel_url ?: "",
        sessionVodLink = session_vod_link ?: "",
        sessionImg = session_img ?: "",
        tags = tags ?: ""
    )
}
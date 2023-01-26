package com.example.ifkakao.data.data_source.mapper

import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.model.*

fun Session.toSessionInfo(): SessionInfo {
    // track -> track List
    var tempTrack = track.replace("[", "")
        .replace("]", "")
        .replace("\"", "")
    val trackArr = tempTrack.split(",")
    val users = arrayListOf<User>()
    if (user1_id != ""){
        users.add(
            User(
                id = user1_id,
                intro = user1_intro,
                img = user1_img,
            )
        )
    }
    if (user2_id != ""){
        users.add(
            User(
                id = user2_id,
                intro = user2_intro,
                img = user2_img,
            )
        )
    }
    if (user3_id != ""){
        users.add(
            User(
                id = user3_id,
                intro = user3_intro,
                img = user3_img,
            )
        )
    }

    return SessionInfo(
        id = id,
        users = users,
        title = title,
        description = description,
        sessionType = SessionType.from(session_type),
        sessionDay = SessionDay.from(session_day),
        ppt = ppt,
        track = trackArr.map { Track.from(it) },
        sessionDate = session_date,
        company = Company.from(company),
        meetupRegisterLink = meetup_register_link,
        liveImgUrl = live_img_url,
        liveQnaUrl = live_qna_url,
        liveChannelUrl = live_channel_url,
        sessionVodLink = session_vod_link,
        sessionImg = session_img,
        tags = tags
    )
}
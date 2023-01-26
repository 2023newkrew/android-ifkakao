package com.example.ifkakao.data.data_source.mapper

import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.model.SessionDay
import com.example.ifkakao.domain.model.SessionInfo
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track

fun Session.toSessionInfo(): SessionInfo {
    // track -> track List
    var tempTrack = track.replace("[", "")
        .replace("]", "")
        .replace("\"", "")
    val trackArr = tempTrack.split(",")

    return SessionInfo(
        id = id,
        user1Id = user1_id,
        user1Img = user1_img,
        user1Intro = user1_intro,
        user2Id = user2_id,
        user2Img = user2_img,
        user2Intro = user2_intro,
        user3Id = user3_id,
        user3Img = user3_img,
        user3Intro = user3_intro,
        title = title,
        description = description,
        sessionType = SessionType.from(session_type),
        sessionDay = SessionDay.from(session_day),
        ppt = ppt,
        track = trackArr.map { Track.from(it) },
        sessionDate = session_date,
        company = company,
        meetupRegisterLink = meetup_register_link,
        liveImgUrl = live_img_url,
        liveQnaUrl = live_qna_url,
        liveChannelUrl = live_channel_url,
        sessionVodLink = session_vod_link,
        sessionImg = session_img,
        tags = tags
    )
}
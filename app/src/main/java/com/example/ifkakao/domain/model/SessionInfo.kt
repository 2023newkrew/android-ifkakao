package com.example.ifkakao.domain.model

import kotlinx.serialization.SerialName

data class SessionInfo(
    val id: Int,
    val user1Id: String,
    val user1Img: String,
    val user1Intro: String,
    val user2Id: String,
    val user2Img: String,
    val user2Intro: String,
    val user3Id: String,
    val user3Img: String,
    val user3Intro: String,
    val title: String,
    val description: String,
    val sessionType: SessionType, // sealed class로 바꾸고 싶음
    val sessionDay: SessionDay, // 1, 2, 3 뿐이다
    val ppt: String,
    val track: List<Track>, // 지정되어 있음
    val sessionDate: Long,
    val company: String, // enum
    val meetupRegisterLink: String,
    val liveImgUrl: String,
    val liveQnaUrl: String,
    val liveChannelUrl: String,
    val sessionVodLink: String,
    val sessionImg: String,
    val tags: String,
)

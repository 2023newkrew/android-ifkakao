package com.example.ifkakao.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    val user2Image: String,
    val user2Id: String,
    val user3Intro: String,
    val user3Image: String,
    val ppt: String,
    val user2Intro: String,
    val user1Intro: String,
    val sessionDay: Int = 0,
    val sessionDate: String,
    val description: String,
    val title: String,
    val sessionType: String,
    val liveImageUrl: String,
    val user1Id: String,
    val user1Image: String,
    val id: Int = 0,
    val track: Set<String> = setOf(),
    val sessionTime: String,
    val company: String,
    val user3Id: String,
    val meetupRegisterLink: String,
    val liveQnAUrl: String,
    val liveChannelUrl: String,
    val sessionVodLink: String,
    val sessionImage: String,
    val tags: String
) : Parcelable
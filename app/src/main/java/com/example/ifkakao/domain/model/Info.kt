package com.example.ifkakao.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    val user2Image: String? = null,
    val user2Id: String? = null,
    val user3Intro: String? = null,
    val user3Image: String? = null,
    val ppt: String? = null,
    val user2Intro: String? = null,
    val user1Intro: String? = null,
    val sessionDay: Int = 0,
    val sessionDate: String? = null,
    val description: String? = null,
    val title: String? = null,
    val sessionType: String? = null,
    val liveImageUrl: String? = null,
    val user1Id: String? = null,
    val user1Image: String? = null,
    val id: Int = 0,
    val track: Set<String> = setOf(),
    val sessionTime: String? = null,
    val company: String? = null,
    val user3Id: String? = null,
    val meetupRegisterLink: String? = null,
    val liveQnAUrl: String? = null,
    val liveChannelUrl: String? = null,
    val sessionVodLink: String? = null,
    val sessionImage: String? = null,
    val tags: String? = null
) : Parcelable
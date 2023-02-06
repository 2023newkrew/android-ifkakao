package com.example.ifkakao.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Session(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val timeStamp: Long = 0L,
    val sessionDay: Int = 0,
    val sessionType: SessionType = SessionType.Preview,
    val tracks: List<Track> = listOf(),
    val company: Company = Company.Kakao,
    val sessionVodLink: String = "",
    val vodThumbUrl: String = "",
    val users: List<User> = listOf(),
    val tags: String = "",
    val pptUrl: String = "",
    val isLike: Boolean = false,
) : Parcelable

fun Session.getTypeAndTracksString(): String {
    return "$sessionType " + tracks.joinToString(
        separator = " "
    ) {
        it.toString()
    }
}

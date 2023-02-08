package com.example.ifkakao.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

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

fun Session.getFullDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this.timeStamp)

    return "%d.%02d.%02d".format(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}

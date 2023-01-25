package com.example.ifkakao.data.data_source.remote.mapper

import com.example.ifkakao.data.data_source.remote.dto.SessionDTO
import com.example.ifkakao.domain.model.*
import java.util.*

fun parseTrackList(string: String): List<Track> {
    return string.removeSurrounding("[", "]")
        .replace("\\", "")
        .replace("\"", "")
        .split(",")
        .filter { it.isNotEmpty() }
        .map { Track.fromString(it) }
}

fun SessionDTO.toSession(): Session {

    // 9yVKHSTkTNmF live_image_url, don't use
    // DvJr7DiMNfhG live_qna_url, don't use

    // WCrhrhpRLki9 meet_up_register_link, don't use
    // p5Z5M0DP08AF live_chanel_url, don't use
    // tsHALc4rYA5Z session_day, don't use

    val users = mutableListOf<User>()
    if (this.kwC3iO8Lbj6D.isNotEmpty()) {
        users.add(
            User(
                id = this.kwC3iO8Lbj6D,
                intro = this::`95fb8cVqD37E`.get(),
                imageUrl = this.PAifZyJwjcmh
            )
        )
    }

    if (this.C90Y851TGjAd.isNotEmpty()) {
        users.add(
            User(
                id = this.C90Y851TGjAd,
                intro = this::`0TNvHfMiEEEE`.get(),
                imageUrl = this::`6SI_gcz2h1l3`.get()
            )
        )
    }

    if (this.b1ERX6ZDBfTy.isNotEmpty()) {
        users.add(
            User(
                id = this.b1ERX6ZDBfTy,
                intro = this.MAjnegS2sUz_,
                imageUrl = this.eHhghHsGMFV0
            )
        )
    }

    return Session(
        id = this.id,
        title = this.U2G0DHalEQHs,
        company = Company.fromString(this::`0xTNIhifz0t7`.get()),
        tracks = parseTrackList(this.GgWcMRm0cNSS),
        sessionVodLink = this.I729yvCqaONJ,
        sessionType = SessionType.fromString(this.INYXb7hNIfMU),
        vodThumbUrl = this.L9WirNOAVF4J,
        tags = this.X_7c2hMWtxne,
        date = Date(this.Yk7Sc6yEUma7),
        pptUrl = this.dc4kWp2OMZBr,
        description = this.pShJsKRFz_mR,
        users = users,
    )
}
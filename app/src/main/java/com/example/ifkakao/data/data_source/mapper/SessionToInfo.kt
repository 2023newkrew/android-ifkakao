package com.example.ifkakao.data.data_source.mapper

import android.annotation.SuppressLint
import com.example.ifkakao.*
import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.model.Info
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Session.toInfo(): Info {
    return Info(
        user2Image = `6SI_gcz2h1l3` ?: "",
        user2Id = C90Y851TGjAd ?: "",
        user3Intro = MAjnegS2sUz_ ?: "",
        user3Image = eHhghHsGMFV0 ?: "",
        ppt = dc4kWp2OMZBr ?: "",
        user2Intro = `0TNvHfMiEEEE` ?: "",
        user1Intro = `95fb8cVqD37E` ?: "",
        sessionDay = tsHALc4rYA5Z,
        sessionDate = when (tsHALc4rYA5Z) {
            1 -> "12.07"
            2 -> "12.08"
            3 -> "12.09"
            else -> ""
        },
        description = pShJsKRFz_mR ?: "",
        title = U2G0DHalEQHs ?: "",
        sessionType = when (INYXb7hNIfMU) {
            TYPE_KEY_KEYNOTE -> TYPE_VALUE_KEYNOTE
            TYPE_KEY_PREVIEW -> TYPE_VALUE_PREVIEW
            TYPE_KEY_TECH -> TYPE_VALUE_TECH
            else -> ""
        },
        liveImageUrl = `9yVKHSTkTNmF` ?: "",
        user1Id = kwC3iO8Lbj6D ?: "",
        user1Image = PAifZyJwjcmh ?: "",
        id = id,
        track =
        GgWcMRm0cNSS?.let { track ->
            track.substring(1, track.length - 1)
                .split("\"")
                .filter { it.isNotEmpty() && it != "," }
                .map {
                    when (it) {
                        TRACK_KEY_1015 -> TRACK_VALUE_1015
                        TRACK_KEY_AI -> TRACK_VALUE_AI
                        TRACK_KEY_BACKEND -> TRACK_VALUE_BACKEND
                        TRACK_KEY_FRONTEND -> TRACK_VALUE_FRONTEND
                        TRACK_KEY_MOBILE -> TRACK_VALUE_MOBILE
                        TRACK_KEY_CLOUD -> TRACK_VALUE_CLOUD
                        TRACK_KEY_DATA -> TRACK_VALUE_DATA
                        TRACK_KEY_BLOCK_CHAIN -> TRACK_VALUE_BLOCK_CHAIN
                        TRACK_KEY_DEV_OPS -> TRACK_VALUE_DEV_OPS
                        TRACK_KEY_ESG -> TRACK_VALUE_ESG
                        TRACK_KEY_GENERAL -> TRACK_VALUE_GENERAL
                        TRACK_KEY_CULTURE -> TRACK_VALUE_CULTURE
                        else -> it
                    }
                }
                .toSet()
        } ?: setOf(),
        sessionTime = SimpleDateFormat("HH:mm").format(Date(Yk7Sc6yEUma7)),
        company = when (`0xTNIhifz0t7`) {
            COMPANY_KEY_KAKAO -> COMPANY_VALUE_KAKAO
            COMPANY_KEY_KAKAO_PA -> COMPANY_VALUE_KAKAO_PA
            COMPANY_KEY_KAKAO_EP -> COMPANY_VALUE_KAKAO_EP
            COMPANY_KEY_KAKAO_M -> COMPANY_VALUE_KAKAO_M
            COMPANY_KEY_KAKAO_B -> COMPANY_VALUE_KAKAO_B
            COMPANY_KEY_KAKAO_R -> COMPANY_VALUE_KAKAO_R
            COMPANY_KEY_KAKAO_G -> COMPANY_VALUE_KAKAO_G
            COMPANY_KEY_KAKAO_ET -> COMPANY_VALUE_KAKAO_ET
            COMPANY_KEY_KU -> COMPANY_VALUE_KU
            COMPANY_KEY_KAKAO_PI -> COMPANY_VALUE_KAKAO_PI
            else -> ""
        },
        user3Id = b1ERX6ZDBfTy ?: "",
        meetupRegisterLink = WCrhrhpRLki9 ?: "",
        liveQnAUrl = DvJr7DiMNfhG ?: "",
        liveChannelUrl = p5Z5M0DP08AF ?: "",
        sessionVodLink = I729yvCqaONJ ?: "",
        sessionImage = L9WirNOAVF4J ?: "",
        tags = X_7c2hMWtxne ?: ""
    )
}
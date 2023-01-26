package com.example.ifkakao.data.data_source.mapper

import android.annotation.SuppressLint
import com.example.ifkakao.data.data_source.dto.Session
import com.example.ifkakao.domain.model.Info
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Session.toInfo() = Info(
    user2Image = `6SI_gcz2h1l3`,
    user2Id = C90Y851TGjAd,
    user3Intro = MAjnegS2sUz_,
    user3Image = eHhghHsGMFV0,
    ppt = dc4kWp2OMZBr,
    user2Intro = `0TNvHfMiEEEE`,
    user1Intro = `95fb8cVqD37E`,
    sessionDay = tsHALc4rYA5Z,
    sessionDate = when (tsHALc4rYA5Z) {
        1 -> "12.07"
        2 -> "12.08"
        3 -> "12.09"
        else -> null
    },
    description = pShJsKRFz_mR,
    title = U2G0DHalEQHs,
    sessionType = INYXb7hNIfMU,
    liveImageUrl = `9yVKHSTkTNmF`,
    user1Id = kwC3iO8Lbj6D,
    user1Image = PAifZyJwjcmh,
    id = id,
    track = GgWcMRm0cNSS,
    sessionTime = SimpleDateFormat("HH:mm").format(Date(Yk7Sc6yEUma7)),
    company = when (`0xTNIhifz0t7`) {
        "dk" -> "카카오"
        "kep" -> "카카오엔터프라이즈"
        "kakaopay" -> "카카오페이"
        "km" -> "카카오모빌리티"
        "kakaobank" -> "카카오뱅크"
        "r" -> "카카오브레인"
        "ku" -> "크러스트 유니버스"
        "dg" -> "카카오게임즈"
        "podo" -> "카카오엔터테인먼트"
        "kpic" -> "카카오픽코마"
        else -> `0xTNIhifz0t7`
    },
    user3Id = b1ERX6ZDBfTy,
    meetupRegisterLink = WCrhrhpRLki9,
    liveQnAUrl = DvJr7DiMNfhG,
    liveChannelUrl = p5Z5M0DP08AF,
    sessionVodLink = I729yvCqaONJ,
    sessionImage = L9WirNOAVF4J,
    tags = X_7c2hMWtxne
)
package com.example.ifkakao.data.data_source.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val id: Int?,
    @SerialName("kwC3iO8Lbj6D")
    val user1_id: String?,
    @SerialName("PAifZyJwjcmh")
    val user1_img: String?,
    @SerialName("95fb8cVqD37E")
    val user1_intro: String?,
    @SerialName("C90Y851TGjAd")
    val user2_id: String?,
    @SerialName("6SI_gcz2h1l3")
    val user2_img: String?,
    @SerialName("0TNvHfMiEEEE")
    val user2_intro: String?,
    @SerialName("b1ERX6ZDBfTy")
    val user3_id: String?,
    @SerialName("eHhghHsGMFV0")
    val user3_img: String?,
    @SerialName("MAjnegS2sUz_")
    val user3_intro: String?,
    @SerialName("U2G0DHalEQHs")
    val title: String?,
    @SerialName("pShJsKRFz_mR")
    val description: String?,
    @SerialName("INYXb7hNIfMU")
    val session_type: String?, // sealed class로 바꾸고 싶음
    @SerialName("tsHALc4rYA5Z")
    val session_day: String?,
    @SerialName("dc4kWp2OMZBr")
    val ppt: String?,
    @SerialName("GgWcMRm0cNSS")
    val track: String?, // sealed class로 바꾸고 싶음
    @SerialName("Yk7Sc6yEUma7")
    val session_date: Long?,
    @SerialName("0xTNIhifz0t7")
    val company: String?, // enum
    @SerialName("WCrhrhpRLki9")
    val meetup_register_link: String?,
    @SerialName("9yVKHSTkTNmF")
    val live_img_url: String?,
    @SerialName("DvJr7DiMNfhG")
    val live_qna_url: String?,
    @SerialName("p5Z5M0DP08AF")
    val live_channel_url: String?,
    @SerialName("I729yvCqaONJ")
    val session_vod_link: String?,
    @SerialName("L9WirNOAVF4J")
    val session_img: String?,
    @SerialName("X_7c2hMWtxne")
    val tags: String?,
)

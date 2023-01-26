package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Track(val value: String) {
    ESG("esg"),
    GENERAL("general"),
    BIG_DATA("bigdata"),
    AI("ai"),
    BE("be"),
    FE("fe"),
    MOBILE("mobile"),
    BLOCK_CHAIN("blockchain"),
    CLOUD("cloud"),
    CULTURE("culture");

    companion object {
        fun from(findValue: String): Track = Track.values().first { it.value == findValue }
    }
}
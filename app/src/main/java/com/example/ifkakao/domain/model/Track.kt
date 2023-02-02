package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Track(val value: String) : FilterType {
    RE1015("re1015"),
    ESG("esg"),
    GENERAL("general"),
    BIG_DATA("bigdata"),
    AI("ai"),
    BE("be"),
    FE("fe"),
    MOBILE("mobile"),
    BLOCK_CHAIN("blockchain"),
    CLOUD("cloud"),
    DEVOPS("devops"),
    CULTURE("culture");

    override fun toString(): String {
        return when (this) {
            AI -> "AI"
            BE -> "백엔드"
            FE -> "프론트엔드"
            MOBILE -> "모바일"
            CLOUD -> "클라우드"
            BIG_DATA -> "빅데이터"
            BLOCK_CHAIN -> "블록체인"
            DEVOPS -> "DevOps"
            ESG -> "ESG"
            GENERAL -> "General"
            CULTURE -> "Culture"
            RE1015 -> "1015장애 회고"
        }
    }

    companion object {
        fun from(findValue: String): Track = Track.values().first { it.value == findValue }
    }
}
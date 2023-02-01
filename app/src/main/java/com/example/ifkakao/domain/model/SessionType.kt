package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SessionType(val value: String) {
    Tech("tech"),
    KeyNote("keynote"),
    Preview("preview"),
    Null("null");

    override fun toString(): String {
        return when (this) {
            Tech -> "기술세션"
            KeyNote -> "키노트"
            Preview -> "프리뷰"
            else -> ""
        }
    }

    companion object {
        fun from(findValue: String): SessionType =
            SessionType.values().first { it.value == findValue }
    }
}



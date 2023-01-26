package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SessionType(val value: String) {
    Tech("tech"),
    KeyNote("keynote"),
    Preview("preview");
    companion object {
        fun from(findValue: String): SessionType = SessionType.values().first { it.value == findValue }
    }
}



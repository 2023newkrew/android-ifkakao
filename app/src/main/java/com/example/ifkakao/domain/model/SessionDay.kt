package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SessionDay(val value: String) {
    DAY_ONE("1"),
    DAY_TWO("2"),
    DAY_THREE("3");
    companion object {
        fun from(findValue: String): SessionDay = SessionDay.values().first { it.value == findValue }
    }
}

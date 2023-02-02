package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class SessionDay(val value: String) {
    DAY_ONE("1"),
    DAY_TWO("2"),
    DAY_THREE("3"),
    Null("null");

    override fun toString(): String {
        return when (this) {
            DAY_ONE -> "12.07"
            DAY_TWO -> "12.08"
            DAY_THREE -> "12.09"
            else -> "--.--"
        }
    }

    companion object {
        fun from(findValue: String): SessionDay =
            SessionDay.values().first { it.value == findValue }
    }
}

package com.example.ifkakao.data.data_source.dto

import kotlinx.serialization.Serializable

@Serializable
enum class SessionType(val value: String) {
    Tech("tech"),
    KeyNote("keynote"),
    Preview("preview"),
}



package com.example.ifkakao.domain.model

enum class SessionType(val alias: String) {
    KeyNote("keynote"), Preview("preview"), TechSession("tech");

    companion object {
        @Throws(NoSuchElementException::class)
        fun fromString(string: String): SessionType {
            return SessionType.values().first { it.alias == string }
        }
    }
}
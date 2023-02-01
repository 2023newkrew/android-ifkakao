package com.example.ifkakao.domain.model

enum class SessionType(val alias: String) : SessionFilterableItem {
    KeyNote("keynote"), Preview("preview"), TechSession("tech");

    override fun toString(): String =
        when (this) {
            KeyNote -> "키노트"
            Preview -> "프리뷰"
            TechSession -> "기술세션"
        }

    companion object {
        @Throws(NoSuchElementException::class)
        fun fromString(string: String): SessionType {
            return SessionType.values().first { it.alias == string }
        }
    }
}
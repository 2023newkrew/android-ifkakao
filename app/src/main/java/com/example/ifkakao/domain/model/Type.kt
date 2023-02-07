package com.example.ifkakao.domain.model

enum class Type(val alias: String) {
    KeyNote("keynote"), Preview("preview"), TechSession("tech");

    override fun toString(): String =
        when (this) {
            KeyNote -> "키노트"
            Preview -> "프리뷰"
            TechSession -> "기술세션"
        }

    companion object {
        fun fromString(string: String): Type {
            return Type.values().first { it.alias == string }
        }
    }
}
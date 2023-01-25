package com.example.ifkakao.domain.model

enum class SessionType(val alias: String) {
    KeyNote("keynote"), Preview("preview"), TechSession("tech");

    companion object {
        fun fromString(string: String): SessionType {
            // 만약 string 으로 SessionType 찾지 못할 시 first 에서 아래 예외 발생됨
            // throw NoSuchElementException("Array contains no element matching the predicate.")
            return SessionType.values().first { it.alias == string }
        }
    }
}
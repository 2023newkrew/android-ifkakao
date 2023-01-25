package com.example.ifkakao.domain.model

enum class Track(val alias: String) {
    Ai("ai"), BackEnd("be"), FrontEnd("fe"), DevOps("devops"),
    Mobile("mobile"), Re1015("re1015"), BlockChain("blockchain"), Cloud("cloud"),
    BigData("bigdata"), Esg("esg"), General("general"), Culture("culture");

    companion object {
        fun fromString(string: String): Track {
            // 만약 string 으로 Track 찾지 못할 시 first 에서 아래 예외 발생됨
            // throw NoSuchElementException("Array contains no element matching the predicate.")
            return Track.values().first { it.alias == string }
        }
    }
}
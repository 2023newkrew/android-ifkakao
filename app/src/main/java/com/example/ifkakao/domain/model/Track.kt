package com.example.ifkakao.domain.model

enum class Track(val alias: String) {
    Ai("ai"), BackEnd("be"), FrontEnd("fe"), DevOps("devops"),
    Mobile("mobile"), Re1015("re1015"), BlockChain("blockchain"), Cloud("cloud"),
    BigData("bigdata"), Esg("esg"), General("general"), Culture("culture");

    companion object {
        @Throws(NoSuchElementException::class)
        fun fromString(string: String): Track {
            return Track.values().first { it.alias == string }
        }
    }
}
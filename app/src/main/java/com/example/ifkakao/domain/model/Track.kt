package com.example.ifkakao.domain.model

enum class Track(val alias: String) {
    Ai("ai"), BackEnd("be"), FrontEnd("fe"), DevOps("devops"),
    Mobile("mobile"), Re1015("re1015"), BlockChain("blockchain"), Cloud("cloud"),
    BigData("bigdata"), Esg("esg"), General("general"), Culture("culture");

    override fun toString(): String =
        when (this) {
            Ai -> "AI"
            BackEnd -> "백앤드"
            FrontEnd -> "프론트앤드"
            DevOps -> "DevOps"
            Mobile -> "모바일"
            Re1015 -> "1015장애 회고"
            BlockChain -> "블록체인"
            Cloud -> "클라우드"
            BigData -> "빅데이터"
            Esg -> "ESG"
            General -> "General"
            Culture -> "Culture"
        }

    companion object {
        @Throws(NoSuchElementException::class)
        fun fromString(string: String): Track {
            return Track.values().first { it.alias == string }
        }
    }
}
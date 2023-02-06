package com.example.ifkakao.domain.model

enum class Company(val value: String) : FilterType {
    KAKAO("dk"),
    KAKAO_PAY("kakaopay"),
    KAKAO_ENTERPRISE("kep"),
    KAKAO_MOBILITY("km"),
    KAKAO_BANK("kakaobank"),
    KAKAO_BRAIN("r"),
    KAKAO_GAMES("dg"),
    KAKAO_ENTERTAINMENT("podo"),
    KRUST_UNIVERSE("ku"),
    KAKAO_PICCOMA("kpic"),
    GREPP("grepp"),
    Null("null");

    override fun toString(): String {
        return when (this) {
            KAKAO -> "카카오"
            KAKAO_ENTERTAINMENT -> "카카오엔터테인먼트"
            KAKAO_BRAIN -> "카카오브레인"
            KAKAO_BANK -> "카카오뱅크"
            KAKAO_PAY -> "카카오페이"
            KAKAO_MOBILITY -> "카카오모빌리티"
            KAKAO_ENTERPRISE -> "카카오엔터프라이즈"
            KAKAO_GAMES -> "카카오게임즈"
            KAKAO_PICCOMA -> "카카오픽코마"
            KRUST_UNIVERSE -> "크러스트 유니버스"
            GREPP -> "grepp"
            else -> "--"
        }
    }

    companion object {
        fun from(findValue: String): Company =
            Company.values().first { it.value == findValue }
    }
}
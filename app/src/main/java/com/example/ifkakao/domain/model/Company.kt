package com.example.ifkakao.domain.model

enum class Company(val value: String) {
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
    GREPP("grepp");

    companion object {
        fun from(findValue: String): Company =
            Company.values().first { it.value == findValue }
    }
}
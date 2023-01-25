package com.example.ifkakao.domain.model

enum class Company(val alias: String) {
    Kakao("dk"), KakaoPay("kakaopay"), KakaoEnterPrise("kep"),
    KakaoMobility("km"), KakaoBank("kakaobank"), KakaoBrain("r"),
    KakaoGames("dg"), KakaoEntertainment("podo"),
    KrustUniverse("ku"), KakaoPicComa("kpic"), Grepp("grepp");

    companion object {
        @Throws(NoSuchElementException::class)
        fun fromString(string: String): Company {
            return Company.values().first { it.alias == string }
        }
    }
}
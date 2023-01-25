package com.example.ifkakao.domain.model

enum class Company(val alias: String) {
    Kakao("dk"), KakaoPay("kakaopay"), KakaoEnterPrise("kep"),
    KakaoMobility("km"), KakaoBank("kakaobank"), KakaoBrain("r"),
    KakaoGames("dg"), KakaoEntertainment("podo"),
    KrustUniverse("ku"), KakaoPicComa("kpic"), Grepp("grepp");

    companion object {
        fun fromString(string: String): Company {
            // 만약 string 으로 company 찾지 못할 시 first 에서 아래 예외 발생됨
            // throw NoSuchElementException("Array contains no element matching the predicate.")
            return Company.values().first { it.alias == string }
        }
    }
}
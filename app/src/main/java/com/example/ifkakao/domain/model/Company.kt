package com.example.ifkakao.domain.model

enum class Company(val alias: String) {
    Kakao("dk"), KakaoPay("kakaopay"), KakaoEnterPrise("kep"),
    KakaoMobility("km"), KakaoBank("kakaobank"), KakaoBrain("r"),
    KakaoGames("dg"), KakaoEntertainment("podo"),
    KrustUniverse("ku"), KakaoPicComa("kpic"), Grepp("grepp");

    override fun toString(): String =
        when (this) {
            Kakao -> "카카오"
            KakaoPay -> "카카오페이"
            KakaoEnterPrise -> "카카오엔터프라이즈"
            KakaoMobility -> "카카오모빌리티"
            KakaoBank -> "카카오뱅크"
            KakaoBrain -> "카카오브레인"
            KakaoGames -> "카카오게임즈"
            KakaoEntertainment -> "카카오엔터테인먼트"
            KrustUniverse -> "크러스트 유니버스"
            KakaoPicComa -> "카카오픽코마"
            Grepp -> "grepp"
        }

    companion object {
        fun fromString(string: String): Company {
            return Company.values().first { it.alias == string }
        }
    }


}
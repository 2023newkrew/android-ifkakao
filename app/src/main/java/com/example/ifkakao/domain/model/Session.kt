package com.example.ifkakao.domain.model

import com.example.ifkakao.presentation.session_list.SessionListFilterItems

data class Session(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val timeStamp: Long = 0L,
    val sessionDay: Int = 0,
    val type: Type = Type.Preview,
    val tracks: List<Track> = listOf(),
    val company: Company = Company.Kakao,
    val sessionVodLink: String = "",
    val vodThumbUrl: String = "",
    val users: List<User> = listOf(),
    val tags: String = "",
    val pptUrl: String = "",
    var isLike: Boolean = false,
)

fun Session.getTypeAndTracksString(): String {
    return "$type " + tracks.joinToString(
        separator = " "
    ) {
        it.toString()
    }
}

fun Session.isFilter(sessionListFilterItems: SessionListFilterItems, likeList: Set<String>): Boolean {
    val type = sessionListFilterItems.isKeynote && type == Type.KeyNote
            || sessionListFilterItems.isPreview && type == Type.Preview
            || sessionListFilterItems.isTechSession && type == Type.TechSession
            || !sessionListFilterItems.isKeynote && !sessionListFilterItems.isPreview && !sessionListFilterItems.isTechSession

    val track = sessionListFilterItems.is1015 && tracks.contains(Track.Re1015)
            || sessionListFilterItems.isAi && tracks.contains(Track.Ai)
            || sessionListFilterItems.isBe && tracks.contains(Track.BackEnd)
            || sessionListFilterItems.isMobile && tracks.contains(Track.Mobile)
            || sessionListFilterItems.isCloud && tracks.contains(Track.Cloud)
            || sessionListFilterItems.isBigData && tracks.contains(Track.BigData)
            || sessionListFilterItems.isBlockChain && tracks.contains(Track.BlockChain)
            || sessionListFilterItems.isDevOps && tracks.contains(Track.DevOps)
            || sessionListFilterItems.isESG && tracks.contains(Track.Esg)
            || sessionListFilterItems.isGeneral && tracks.contains(Track.General)
            || sessionListFilterItems.isCulture && tracks.contains(Track.Culture)
            || !sessionListFilterItems.is1015 && !sessionListFilterItems.isAi && !sessionListFilterItems.isBe && !sessionListFilterItems.isFe && !sessionListFilterItems.isMobile && !sessionListFilterItems.isCloud && !sessionListFilterItems.isBigData && !sessionListFilterItems.isBlockChain && !sessionListFilterItems.isDevOps && !sessionListFilterItems.isESG && !sessionListFilterItems.isGeneral && !sessionListFilterItems.isCulture

    val company = sessionListFilterItems.isKakao && company == Company.Kakao
            || sessionListFilterItems.isKakaoPay && company == Company.KakaoPay
            || sessionListFilterItems.isKakaoEnterprise && company == Company.KakaoEnterPrise
            || sessionListFilterItems.isKakaoMobility && company == Company.KakaoMobility
            || sessionListFilterItems.isKakaoBank && company == Company.KakaoBank
            || sessionListFilterItems.isKakaoBrain && company == Company.KakaoBrain
            || sessionListFilterItems.isKakaoGames && company == Company.KakaoGames
            || sessionListFilterItems.isKakaoEntertainment && company == Company.KakaoEntertainment
            || sessionListFilterItems.isKrustUniverse && company == Company.KrustUniverse
            || sessionListFilterItems.isKakaoPickoma && company == Company.KakaoPicComa
            || !sessionListFilterItems.isKakao && !sessionListFilterItems.isKakaoPay && !sessionListFilterItems.isKakaoEnterprise && !sessionListFilterItems.isKakaoMobility && !sessionListFilterItems.isKakaoBank && !sessionListFilterItems.isKakaoBrain && !sessionListFilterItems.isKakaoGames && !sessionListFilterItems.isKakaoEntertainment && !sessionListFilterItems.isKrustUniverse && !sessionListFilterItems.isKakaoPickoma

    val like = sessionListFilterItems.isLikeItem && id.toString() in likeList
            ||!sessionListFilterItems.isLikeItem

    return type && track && company && like
}
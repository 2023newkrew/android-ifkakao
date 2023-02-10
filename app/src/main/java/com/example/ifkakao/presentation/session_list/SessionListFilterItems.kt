package com.example.ifkakao.presentation.session_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SessionListFilterItems(
    val isKeynote: Boolean = false,
    val isPreview: Boolean = false,
    val isTechSession: Boolean = false,

    val is1015: Boolean = false,
    val isAi: Boolean = false,
    val isBe: Boolean = false,
    val isFe: Boolean = false,
    val isMobile: Boolean = false,
    val isCloud: Boolean = false,
    val isBigData: Boolean = false,
    val isBlockChain: Boolean = false,
    val isDevOps: Boolean = false,
    val isESG: Boolean = false,
    val isGeneral: Boolean = false,
    val isCulture: Boolean = false,

    val isKakao: Boolean = false,
    val isKakaoPay: Boolean = false,
    val isKakaoEnterprise: Boolean = false,
    val isKakaoMobility: Boolean = false,
    val isKakaoBank: Boolean = false,
    val isKakaoBrain: Boolean = false,
    val isKakaoGames: Boolean = false,
    val isKakaoEntertainment: Boolean = false,
    val isKrustUniverse: Boolean = false,
    val isKakaoPickoma: Boolean = false,

    val isLikeItem: Boolean = false,

    val isDateOne: Boolean = false,
    val isDateTwo: Boolean = false,
    val isDateThree: Boolean = false,

    ) : Parcelable

fun SessionListFilterItems.countType(): Int {
    var result = 0
    if (isKeynote)
        result += 1
    if (isPreview)
        result += 1
    if (isTechSession)
        result += 1
    return result
}

fun SessionListFilterItems.count(): Int {
    var result = 0
    if (isKeynote)
        result += 1
    if (isPreview)
        result += 1
    if (isTechSession)
        result += 1
    if (is1015)
        result += 1
    if (isAi)
        result += 1
    if (isBe)
        result += 1
    if (isFe)
        result += 1
    if (isMobile)
        result += 1
    if (isCloud)
        result += 1
    if (isBigData)
        result += 1
    if (isBlockChain)
        result += 1
    if (isDevOps)
        result += 1
    if (isESG)
        result += 1
    if (isGeneral)
        result += 1
    if (isCulture)
        result += 1
    if (isKakao)
        result += 1
    if (isKakaoEnterprise)
        result += 1
    if (isKakaoMobility)
        result += 1
    if (isKakaoBank)
        result += 1
    if (isKakaoBrain)
        result += 1
    if (isKakaoGames)
        result += 1
    if (isKakaoEnterprise)
        result += 1
    if (isKrustUniverse)
        result += 1
    if (isKakaoPickoma)
        result += 1
    return result
}

fun SessionListFilterItems.countTrack(): Int {
    var result = 0
    if (is1015)
        result += 1
    if (isAi)
        result += 1
    if (isBe)
        result += 1
    if (isFe)
        result += 1
    if (isMobile)
        result += 1
    if (isCloud)
        result += 1
    if (isBigData)
        result += 1
    if (isBlockChain)
        result += 1
    if (isDevOps)
        result += 1
    if (isESG)
        result += 1
    if (isGeneral)
        result += 1
    if (isCulture)
        result += 1
    return result
}

fun SessionListFilterItems.countCompany(): Int {
    var result = 0
    if (isKakao)
        result += 1
    if (isKakaoEnterprise)
        result += 1
    if (isKakaoMobility)
        result += 1
    if (isKakaoBank)
        result += 1
    if (isKakaoBrain)
        result += 1
    if (isKakaoGames)
        result += 1
    if (isKakaoEnterprise)
        result += 1
    if (isKrustUniverse)
        result += 1
    if (isKakaoPickoma)
        result += 1
    return result
}
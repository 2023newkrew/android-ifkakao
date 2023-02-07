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
) : Parcelable

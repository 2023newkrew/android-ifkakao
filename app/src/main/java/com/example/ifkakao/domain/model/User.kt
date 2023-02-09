package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

// TODO: Parcelable로 변경해야 함
@Serializable
data class User(
    val id: String,
    val intro: String,
    val img: String,
) : java.io.Serializable

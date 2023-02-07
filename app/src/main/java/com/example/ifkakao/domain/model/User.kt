package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val intro: String,
    val img: String,
) : java.io.Serializable

package com.example.ifkakao.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val intro: String,
    val imageUrl: String,
) : Parcelable
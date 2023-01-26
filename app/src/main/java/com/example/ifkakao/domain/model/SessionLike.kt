package com.example.ifkakao.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionLike(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val likeSessionId: Int,
)

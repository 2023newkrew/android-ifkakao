package com.example.ifkakao.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Like(
    @PrimaryKey val sessionId: Int
)

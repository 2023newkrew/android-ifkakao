package com.example.ifkakao.domain.repository

import com.example.ifkakao.domain.model.Like
import kotlinx.coroutines.flow.Flow

interface LikeRepository {

    fun getLikes(): Flow<List<Like>>

    suspend fun insertLike(like: Like)

    suspend fun deleteLike(like: Like)
}
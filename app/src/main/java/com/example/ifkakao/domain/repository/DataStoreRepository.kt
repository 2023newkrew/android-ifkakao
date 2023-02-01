package com.example.ifkakao.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getLike(id: String): Flow<Boolean>

    suspend fun putLike(id: String, like: Boolean)
}
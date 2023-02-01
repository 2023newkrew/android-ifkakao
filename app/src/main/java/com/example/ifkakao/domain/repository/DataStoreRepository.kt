package com.example.ifkakao.domain.repository

interface DataStoreRepository {
    suspend fun getLike(id: String): Boolean

    suspend fun putLike(id: String, like: Boolean)
}
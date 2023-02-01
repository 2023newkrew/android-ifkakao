package com.example.ifkakao.domain.use_case

import com.example.ifkakao.domain.repository.DataStoreRepository

class PutLikeUseCase(private val repository: DataStoreRepository) {
    suspend operator fun invoke(id: String, like: Boolean) = repository.putLike(id, like)
}
package com.example.ifkakao.domain.use_case

import com.example.ifkakao.domain.repository.DataStoreRepository

class GetLikeUseCase(private val repository: DataStoreRepository) {
    suspend operator fun invoke(id: String): Boolean = repository.getLike(id)
}
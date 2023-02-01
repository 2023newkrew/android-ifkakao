package com.example.ifkakao.domain.use_case

import com.example.ifkakao.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class GetLikeUseCase(private val repository: DataStoreRepository) {
    operator fun invoke(id: String): Flow<Boolean> = repository.getLike(id)
}
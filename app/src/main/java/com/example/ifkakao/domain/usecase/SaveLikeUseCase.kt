package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.repository.RemoteRepository

class SaveLikeUseCase(private val repository: RemoteRepository) {
    operator fun invoke(id: Int): Boolean {
        return repository.saveLike(id)
    }
}
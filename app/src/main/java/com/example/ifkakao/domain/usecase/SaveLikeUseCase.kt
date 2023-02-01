package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.repository.LocalRepository

class SaveLikeUseCase(private val repository: LocalRepository) {
    operator fun invoke(set: Set<String>): Boolean {
        return repository.saveLike(set)
    }
}
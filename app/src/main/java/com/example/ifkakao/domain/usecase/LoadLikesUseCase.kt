package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.repository.LocalRepository

class LoadLikesUseCase(private val repository: LocalRepository) {
    operator fun invoke(): MutableSet<String>? {
        return repository.loadLikes()
    }
}
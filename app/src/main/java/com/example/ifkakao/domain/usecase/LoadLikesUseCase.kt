package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.repository.RemoteRepository

class LoadLikesUseCase(private val repository: RemoteRepository) {
    operator fun invoke(): List<Int> {
        return repository.loadLikes()
    }
}
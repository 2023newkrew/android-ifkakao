package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.model.Like
import com.example.ifkakao.domain.repository.LikeRepository

class DeleteLikeUseCase(private val repository: LikeRepository) {
    suspend operator fun invoke(like: Like) = repository.deleteLike(like)
}
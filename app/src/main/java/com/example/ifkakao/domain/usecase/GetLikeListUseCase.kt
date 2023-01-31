package com.example.ifkakao.domain.usecase

import com.example.ifkakao.domain.model.Like
import com.example.ifkakao.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikeListUseCase @Inject constructor(private val repository: LikeRepository) {
    operator fun invoke(): Flow<List<Like>> = repository.getLikes()
}
package com.example.ifkakao.data.repository

import androidx.annotation.WorkerThread
import com.example.ifkakao.data.data_source.dao.LikeDao
import com.example.ifkakao.domain.model.Like
import com.example.ifkakao.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(private val dao: LikeDao) : LikeRepository {
    @WorkerThread
    override fun getLikes(): Flow<List<Like>> {
        return dao.getLikes()
    }

    @WorkerThread
    override suspend fun insertLike(like: Like) {
        return dao.insertLike(like)
    }

    @WorkerThread
    override suspend fun deleteLike(like: Like) {
        return dao.deleteLike(like)
    }
}
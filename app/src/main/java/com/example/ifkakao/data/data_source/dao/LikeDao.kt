package com.example.ifkakao.data.data_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ifkakao.domain.model.Like
import kotlinx.coroutines.flow.Flow

@Dao
interface LikeDao {
    @Query("SELECT * FROM `like`")
    fun getLikes(): Flow<List<Like>>

    @Insert
    suspend fun insertLike(like: Like)

    @Delete
    suspend fun deleteLike(like: Like)
}
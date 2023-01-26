package com.example.ifkakao.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ifkakao.domain.model.SessionLike

@Dao
interface SessionLikeDao {
    @Query("SELECT * FROM SessionLike")
    suspend fun getSessionLikes(): List<SessionLike>

    @Query("SELECT * FROM SessionLike WHERE likeSessionId = :likeSessionId")
    suspend fun isSessionLike(likeSessionId: Int): SessionLike?

    @Query("INSERT INTO SessionLike(likeSessionId) values( :likeSessionId )")
    suspend fun insertSessionLike(likeSessionId: Int)

    @Query("DELETE FROM SessionLike WHERE likeSessionId = :likeSessionId")
    suspend fun deleteSessionLike(likeSessionId: Int)

}
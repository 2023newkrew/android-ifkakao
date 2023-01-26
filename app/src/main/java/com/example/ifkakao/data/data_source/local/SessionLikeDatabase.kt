package com.example.ifkakao.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ifkakao.domain.model.SessionLike

@Database(entities = [SessionLike::class], version = 2, exportSchema = false)
abstract class SessionLikeDatabase : RoomDatabase() {
    abstract val sessionLikeDao: SessionLikeDao

    companion object {
        const val DATABASE_NAME = "session_like_db"
    }
}

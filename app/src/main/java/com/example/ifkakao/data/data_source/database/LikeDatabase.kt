package com.example.ifkakao.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ifkakao.data.data_source.dao.LikeDao
import com.example.ifkakao.domain.model.Like

@Database(entities = [Like::class], version = 1)
abstract class LikeDatabase : RoomDatabase() {

    abstract fun likeDao(): LikeDao
}
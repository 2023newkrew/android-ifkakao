package com.example.ifkakao.data.repository

import android.content.SharedPreferences
import com.example.ifkakao.domain.repository.LocalRepository

class LocalRepositoryImpl(private val sharedPreferences: SharedPreferences) : LocalRepository {

    override fun loadLikes(): MutableSet<String> {
        val set = setOf<String>()
        return try {
            sharedPreferences.getStringSet("Likes", set) ?: setOf<String>().toMutableSet()
        } catch (e: Exception) {
            emptySet<String>().toMutableSet()
        }
    }

    override fun saveLike(set: Set<String>): Boolean =
        try {
            sharedPreferences.edit().putStringSet("Likes", set)
                .apply()
            true
        } catch (e: Exception) {
            false
        }

}
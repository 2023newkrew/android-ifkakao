package com.example.ifkakao.domain.repository

interface LocalRepository {
    fun loadLikes(): MutableSet<String>?

    fun saveLike(set: Set<String>): Boolean

}
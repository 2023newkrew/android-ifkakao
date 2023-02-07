package com.example.ifkakao.domain.repository

interface WindowRepository {
    fun getIsDualPane(): Boolean

    fun getWidth(): Int
}
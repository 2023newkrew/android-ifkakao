package com.example.ifkakao.presentation.home

data class HomeState(
    val playbackPosition: Long = 0,
    val currentWindow: Int = 0,
    val playWhenReady: Boolean = true
)

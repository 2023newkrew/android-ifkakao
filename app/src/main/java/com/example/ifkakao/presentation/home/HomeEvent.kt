package com.example.ifkakao.presentation.home

sealed class HomeEvent {
    class ReleasePlayer(
        val currentWindow: Int,
        val playbackPosition: Long,
        val playWhenReady: Boolean
    ) : HomeEvent()
}

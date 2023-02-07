package com.example.ifkakao.presentation.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val _videoPlayerState = MutableStateFlow(VideoPlayerState())
    val videoPlayerState = _videoPlayerState.asStateFlow()

    fun updateVideoPlayerState(
        currentWindow: Int,
        playbackPosition: Long,
        playWhenReady: Boolean
    ) {
        _videoPlayerState.value = videoPlayerState.value.copy(
            currentWindow = currentWindow,
            playbackPosition = playbackPosition,
            playWhenReady = playWhenReady,
        )
    }

    fun setVideoUrl(videoUrl: String) {
        _videoPlayerState.value = videoPlayerState.value.copy(
            videoUrl = videoUrl
        )
    }
}

data class VideoPlayerState(
    val currentWindow: Int = 0,
    val playbackPosition: Long = 0L,
    val playWhenReady: Boolean = true,
    val videoUrl: String = "",
)
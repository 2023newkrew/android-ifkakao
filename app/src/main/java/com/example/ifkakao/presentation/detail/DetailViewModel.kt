package com.example.ifkakao.presentation.detail

import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val _videoPlayerState = MutableStateFlow(VideoPlayerState())
    val videoPlayerState = _videoPlayerState.asStateFlow()

    private val _detailFragmentState = MutableStateFlow(DetailFragmentState())
    val detailFragmentState = _detailFragmentState.asStateFlow()

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

    fun setOrientation(orientation: Int) {
        _detailFragmentState.value = detailFragmentState.value.copy(
            orientation = orientation
        )
    }
}

data class VideoPlayerState(
    val currentWindow: Int = 0,
    val playbackPosition: Long = 0L,
    val playWhenReady: Boolean = true,
    val videoUrl: String = "",
)

data class DetailFragmentState(
    val orientation: Int = Configuration.ORIENTATION_PORTRAIT
)
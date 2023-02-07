package com.example.ifkakao.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val VIDEO_URL_PORT = "https://t1.kakaocdn.net/inhouse_daglona/ifkakao_2022/assets" +
        "/mobile_2nd_day_1080x1848.mp4"
const val VIDEO_URL_LAND =
    "https://t1.kakaocdn.net/inhouse_daglona/ifkakao_2022/assets/web_2nd_day_3840x2160.mp4"

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ReleasePlayer -> {
                _homeState.value = homeState.value.copy(
                    currentWindow = event.currentWindow,
                    playbackPosition = event.playbackPosition,
                    playWhenReady = event.playWhenReady
                )
            }
        }
    }
}
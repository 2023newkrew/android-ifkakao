package com.example.ifkakao.presentation.session_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.usecase.ChangeLikeSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    private val changeLikeSessionUseCase: ChangeLikeSessionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _session = MutableStateFlow(Session())
    val session: StateFlow<Session> = _session.asStateFlow()

    init {
        savedStateHandle.get<Session>("session")?.let {
            _session.value = it
        }
    }

    fun onEvent(event: SessionDetailEvent) {
        when (event) {
            is SessionDetailEvent.LikeSession -> {
                viewModelScope.launch {
                    changeLikeSessionUseCase(event.session, true)
                    _session.value = _session.value.copy(
                        isLike = true
                    )
                }
            }
            is SessionDetailEvent.UnLikeSession -> {
                viewModelScope.launch {
                    changeLikeSessionUseCase(event.session, false)
                    _session.value = _session.value.copy(
                        isLike = false
                    )
                }
            }
        }
    }
}


val Session.youtubeId: String
    get() {
        return this.sessionVodLink.takeLastWhile { it != '/' }
    }

val Session.sessionWebLink: String
    get() {
        return "https://if.kakao.com/2022/session/${this.id}"
    }
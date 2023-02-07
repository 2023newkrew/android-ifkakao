package com.example.ifkakao.presentation.session_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.model.Session
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _session = MutableStateFlow(Session())
    val session: StateFlow<Session> = _session.asStateFlow()

    init {
        savedStateHandle.get<Session>("session")?.let {
            _session.value = it
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
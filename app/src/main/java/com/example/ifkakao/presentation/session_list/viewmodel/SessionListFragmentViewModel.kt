package com.example.ifkakao.presentation.session_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.data.mapper.toSession
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.presentation.session_list.ui_state.SessionListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListFragmentViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {

    private val _sessionListUiState: MutableStateFlow<SessionListUiState> =
        MutableStateFlow(
            SessionListUiState(
                sessions = emptyList(),
                selectType = emptyList(),
                selectTrack = emptyList(),
                selectCompany = emptyList(),
            )
        )
    var sessionListUiState = _sessionListUiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            val responseList = mutableListOf<Session>()
            for (sessionResult in sessionRepository.getSessions()) {
                responseList.add(sessionResult.toSession())
            }
            _sessionListUiState.value = sessionListUiState.value.copy( sessions = responseList )
        }
    }
}
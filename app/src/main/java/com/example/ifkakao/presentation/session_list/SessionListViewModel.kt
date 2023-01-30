package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.usecase.ChangeLikeSessionUseCase
import com.example.ifkakao.domain.usecase.GetSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SessionListViewModel @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val changeLikeSessionUseCase: ChangeLikeSessionUseCase
) : ViewModel() {

    private val _sessionListState = MutableStateFlow<SessionListState>(SessionListState())
    val sessionListState: StateFlow<SessionListState> = _sessionListState.asStateFlow()

    init {
        loadSessions()
    }

    private var getSessionsJob: Job? = null
    private fun loadSessions() {
        getSessionsJob?.cancel()

        getSessionsJob = viewModelScope.launch {
            val sessionList = getSessionsUseCase(
                sessionDay = sessionListState.value.sessionDay,
                sessionFilter = sessionListState.value.sessionFilter,
                showLikeOnly = sessionListState.value.showLikeOnly
            )

            _sessionListState.value = sessionListState.value.copy(
                sessionList = sessionList
            )
        }
    }

    fun onEvent(event: SessionListEvent) {
        when (event) {
            SessionListEvent.FilterInitialize -> {
                _sessionListState.value = sessionListState.value.copy(
                    sessionFilter = SessionFilter()
                )
            }
            is SessionListEvent.FilterSessions -> {
                _sessionListState.value = sessionListState.value.copy(
                    sessionFilter = event.sessionFilter
                )
            }
            is SessionListEvent.ShowLikeSessionsOnly -> {
                _sessionListState.value = sessionListState.value.copy(
                    showLikeOnly = event.isLikeSessionsOnly
                )
            }
            is SessionListEvent.ChangeSessionDay -> {
                _sessionListState.value = sessionListState.value.copy(
                    sessionDay = event.sessionDay
                )
            }
            is SessionListEvent.LikeSession -> {
                viewModelScope.launch {
                    changeLikeSessionUseCase(
                        event.session,
                        isLike = true
                    )
                }
            }
            is SessionListEvent.UnLikeSession -> {
                viewModelScope.launch {
                    changeLikeSessionUseCase(
                        event.session,
                        isLike = false
                    )
                }
            }
        }
        loadSessions()
    }
}
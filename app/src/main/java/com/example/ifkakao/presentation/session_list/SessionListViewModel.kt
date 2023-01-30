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
    private val _sessionDayState = MutableStateFlow(0)
    val sessionDayState: StateFlow<Int> = _sessionDayState.asStateFlow()

    private val _sessionFilterState = MutableStateFlow(SessionFilter())
    val sessionFilterState: StateFlow<SessionFilter> = _sessionFilterState.asStateFlow()

    private val _showLikeOnlyState = MutableStateFlow(false)
    val showLikeOnlyState: StateFlow<Boolean> = _showLikeOnlyState.asStateFlow()

    private val _sessionListState = MutableStateFlow<List<Session>>(listOf())
    val sessionListState: StateFlow<List<Session>> = _sessionListState.asStateFlow()


    init {
        loadSessions()
    }

    private var getSessionsJob: Job? = null
    private fun loadSessions() {
        getSessionsJob?.cancel()

        getSessionsJob = viewModelScope.launch {
            _sessionListState.value = getSessionsUseCase(
                sessionDay = sessionDayState.value,
                sessionFilter = sessionFilterState.value,
                showLikeOnly = showLikeOnlyState.value
            )
        }
    }

    fun onEvent(event: SessionListEvent) {
        when (event) {
            SessionListEvent.FilterInitialize -> {
                _sessionFilterState.value = SessionFilter()
                loadSessions()
            }
            is SessionListEvent.FilterSessions -> {
                _sessionFilterState.value = event.sessionFilter
                loadSessions()
            }
            is SessionListEvent.ShowLikeSessionsOnly -> {
                _showLikeOnlyState.value = event.isLikeSessionsOnly
                loadSessions()
            }
            is SessionListEvent.ChangeSessionDay -> {
                _sessionDayState.value = event.sessionDay
                loadSessions()
            }
            // 좋아요 여부 변경 시 리스트 전체를 다시 로드 하는 것이 옳은가..?
            is SessionListEvent.LikeSession -> {
                viewModelScope.launch {
                    changeLikeSessionUseCase(
                        event.session,
                        isLike = true
                    )
                }
                loadSessions()
            }
            is SessionListEvent.UnLikeSession -> {
                viewModelScope.launch {
                    changeLikeSessionUseCase(
                        event.session,
                        isLike = false
                    )
                }
                loadSessions()
            }

        }
    }
}
package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.usecase.GetSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SessionListViewModel @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
) : ViewModel() {
    private val _sessionDayState = MutableStateFlow(0)
    val sessionDayState: StateFlow<Int> = _sessionDayState.asStateFlow()


    private val _sessionListState = MutableStateFlow<List<Session>>(listOf())
    val sessionListState: StateFlow<List<Session>> = _sessionListState.asStateFlow()


    init {
        viewModelScope.launch {
            _sessionListState.value = getSessionsUseCase(
                sessionFilter = SessionFilter(),
                showLikeOnly = false
            )
        }
    }

    fun onEvent(event: SessionListEvent) {
        when (event) {
            is SessionListEvent.Filter -> TODO()
            SessionListEvent.FilterInitialize -> TODO()
            is SessionListEvent.Like -> TODO()
            is SessionListEvent.UnLike -> TODO()
        }
    }
}
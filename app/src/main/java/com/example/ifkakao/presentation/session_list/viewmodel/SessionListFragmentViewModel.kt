package com.example.ifkakao.presentation.session_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.data.mapper.toSession
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.repository.SessionRepository
import com.example.ifkakao.presentation.session_list.ui_state.FilterState
import com.example.ifkakao.presentation.session_list.ui_state.SessionListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListFragmentViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {

    lateinit var originalSessions: List<Session>

    private val _filterState: MutableStateFlow<FilterState> =
        MutableStateFlow(
            FilterState(
                selectType = emptySet(),
                selectTrack = emptySet(),
                selectCompany = emptySet(),
            )
        )
    var filterState = _filterState.asStateFlow()


    private val _sessionListState: MutableStateFlow<SessionListState> =
        MutableStateFlow(
            SessionListState(
                sessions = emptyList(),
            )
        )
    var sessionListState = _sessionListState.asStateFlow()

    init {
        viewModelScope.launch {
            filterState.collectLatest { collectFilterState ->
                var filteredSessions = sessionListState.value.copy().sessions.filter {
                    it.track.containsAll(collectFilterState.selectTrack)
                }
                _sessionListState.value = sessionListState.value.copy(
                    sessions = filteredSessions
                )
            }
        }
    }

    fun load() {
        viewModelScope.launch {
            val responseList = mutableListOf<Session>()
            for (sessionResult in sessionRepository.getSessions()) {
                responseList.add(sessionResult.toSession())
            }
            _sessionListState.value = sessionListState.value.copy(sessions = responseList)
            originalSessions = responseList
        }
    }

    fun addTrackFilter(trackFilter: String) {
        var newTrackSet = filterState.value.copy().selectTrack.plus(trackFilter)
        _filterState.value = filterState.value.copy(
            selectTrack = newTrackSet
        )
    }
    fun deleteTrackFilter(trackFilter: String) {
        var newTrackSet = filterState.value.copy().selectTrack.minus(trackFilter)
        _sessionListState.value = sessionListState.value.copy(sessions = originalSessions)
        _filterState.value = filterState.value.copy(
            selectTrack = newTrackSet
        )
    }
}
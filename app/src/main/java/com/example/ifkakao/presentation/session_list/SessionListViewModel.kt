package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionFilterableItem
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
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
    private val changeLikeSessionUseCase: ChangeLikeSessionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _sessionListState = MutableStateFlow(SessionListState())
    val sessionListState: StateFlow<SessionListState> = _sessionListState.asStateFlow()

    private val _sessionFilterState = MutableStateFlow(SessionFilter())
    val sessionFilterState: StateFlow<SessionFilter> = _sessionFilterState.asStateFlow()

    init {
        savedStateHandle.get<String>("session_type")?.let {
            if (it.isNotEmpty()) {
                _sessionFilterState.value = sessionFilterState.value.copy(
                    sessionTypes = setOf(SessionType.fromString(it))
                )
            }

        }
        savedStateHandle.get<String>("track")?.let {
            if (it.isNotEmpty()) {
                _sessionFilterState.value = sessionFilterState.value.copy(
                    tracks = setOf(Track.fromString(it))
                )
            }
        }

        _sessionListState.value = sessionListState.value.copy(
            isFilterEnable = sessionFilterState.value.sessionTypes.isNotEmpty() ||
                    sessionFilterState.value.tracks.isNotEmpty() ||
                    sessionFilterState.value.companies.isNotEmpty()
        )

        loadSessions()
    }

    private var getSessionsJob: Job? = null
    private fun loadSessions() {
        getSessionsJob?.cancel()

        getSessionsJob = viewModelScope.launch {
            val sessionList = getSessionsUseCase(
                sessionDay = sessionListState.value.sessionDay,
                sessionFilter = sessionFilterState.value,
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
                _sessionFilterState.value = SessionFilter()
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
            is SessionListEvent.AddFilterItem -> {
                sessionFilterItemAddOrRemove(true, event.filterableItem)

            }
            is SessionListEvent.RemoveFilterItem -> {
                sessionFilterItemAddOrRemove(false, event.filterableItem)
            }
        }
        _sessionListState.value = sessionListState.value.copy(
            isFilterEnable = sessionFilterState.value.sessionTypes.isNotEmpty() ||
                    sessionFilterState.value.tracks.isNotEmpty() ||
                    sessionFilterState.value.companies.isNotEmpty()
        )

        loadSessions()
    }

    private fun sessionFilterItemAddOrRemove(
        isAdd: Boolean,
        filterableItem: SessionFilterableItem
    ) {
        when (filterableItem) {
            is Company -> {
                val companies = sessionFilterState.value.companies.toMutableSet()
                if (isAdd)
                    companies.add(filterableItem)
                else
                    companies.remove(filterableItem)
                _sessionFilterState.value = sessionFilterState.value.copy(
                    companies = companies
                )
            }
            is SessionType -> {
                val sessionTypes = sessionFilterState.value.sessionTypes.toMutableSet()
                if (isAdd)
                    sessionTypes.add(filterableItem)
                else
                    sessionTypes.remove(filterableItem)
                _sessionFilterState.value = sessionFilterState.value.copy(
                    sessionTypes = sessionTypes
                )
            }
            is Track -> {
                val tracks = sessionFilterState.value.tracks.toMutableSet()
                if (isAdd)
                    tracks.add(filterableItem)
                else
                    tracks.remove(filterableItem)
                _sessionFilterState.value = sessionFilterState.value.copy(
                    tracks = tracks
                )
            }
        }

    }
}
package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.model.isFilter
import com.example.ifkakao.domain.usecase.LoadLikesUseCase
import com.example.ifkakao.domain.usecase.LoadSessionsUseCase
import com.example.ifkakao.domain.usecase.SaveLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionListViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    val loadLikesUseCase: LoadLikesUseCase,
    val loadSessionsUseCase: LoadSessionsUseCase,
    val saveLikeUseCase: SaveLikeUseCase,
) : ViewModel() {

    private val _filterItems: MutableStateFlow<SessionListFilterItems>
    = MutableStateFlow(SessionListFilterItems())
    val filterItems by lazy {
        _filterItems.asStateFlow()
    }

    private lateinit var sessionList: List<Session>

    private val _showSessionList: MutableStateFlow<List<Session>>
    = MutableStateFlow(emptyList())
    val showSessionList by lazy {
        _showSessionList.asStateFlow()
    }

    init {
        savedStateHandle.get<SessionListFilterItems>("FilterItems")?.let {
            _filterItems.value = it
        } ?: run {
            _filterItems.value = SessionListFilterItems()
        }

        viewModelScope.launch {
            sessionList = loadSessionsUseCase()
            filterItems.collectLatest { filter ->
                _showSessionList.value = sessionList.filter { it.isFilter(filter) }
            }
        }
    }


}
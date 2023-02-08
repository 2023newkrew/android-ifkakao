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
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class SessionListViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    val loadLikesUseCase: LoadLikesUseCase,
    val loadSessionsUseCase: LoadSessionsUseCase,
    val saveLikeUseCase: SaveLikeUseCase,
) : ViewModel() {

    private val _filterItems: MutableStateFlow<SessionListFilterItems> =
        MutableStateFlow(SessionListFilterItems())
    val filterItems by lazy {
        _filterItems.asStateFlow()
    }

    private val _showSessionList: MutableStateFlow<List<Session>> = MutableStateFlow(emptyList())
    val showSessionList by lazy {
        _showSessionList.asStateFlow()
    }

    private val _likeList: MutableStateFlow<Set<String>> = MutableStateFlow(emptySet())
    val likeList by lazy {
        _likeList.asStateFlow()
    }

    private lateinit var sessionList: List<Session>

    init {
        savedStateHandle.get<SessionListFilterItems>("FilterItems")?.let {
            _filterItems.value = it
        } ?: run {
            _filterItems.value = SessionListFilterItems()
        }

        viewModelScope.launch {
            _likeList.value = loadLikesUseCase()
            sessionList = loadSessionsUseCase()

            _showSessionList.value = sessionList.filter {
                it.isFilter(
                    filterItems.value,
                    _likeList.value
                )
            }.map {
                if(_likeList.value.contains(it.id.toString()))
                    it.copy(isLike = true)
                else
                    it
            }
        }
    }


    fun likeToggle(id: Int) {
        val set = _likeList.value.toMutableSet()
        if (id.toString() in set) {
            set.remove(id.toString())
            _showSessionList.value = showSessionList.value.map {
                if (it.id == id) {
                    it.copy(isLike = false)
                }
                else
                    it
            }
        } else {
            set.add(id.toString())
            _showSessionList.value = showSessionList.value.map {
                if (it.id == id) {
                    it.copy(isLike = true)
                }
                else
                    it
            }
        }
        println(set)
        _likeList.value = set
        saveLikeUseCase(set)


    }
}
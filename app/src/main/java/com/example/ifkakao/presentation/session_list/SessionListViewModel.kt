package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import com.example.ifkakao.domain.usecase.LoadLikesUseCase
import com.example.ifkakao.domain.usecase.LoadSessionsUseCase
import com.example.ifkakao.domain.usecase.SaveLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionListViewModel
@Inject constructor(
    val loadLikesUseCase: LoadLikesUseCase,
    val loadSessionsUseCase: LoadSessionsUseCase,
    val saveLikeUseCase: SaveLikeUseCase,
) : ViewModel() {

    private lateinit var _filterItems: MutableStateFlow<SessionListFilterItems>
    private val filterItems by lazy {
        _filterItems.asStateFlow()
    }

    private lateinit var sessionList: List<ResultSession>
    init{
        viewModelScope.launch {
            sessionList = loadSessionsUseCase()
        }
    }

    fun initFilterItems(filterItems: SessionListFilterItems) {
        _filterItems = MutableStateFlow(filterItems)
    }


}
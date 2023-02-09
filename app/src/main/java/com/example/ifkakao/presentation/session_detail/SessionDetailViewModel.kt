package com.example.ifkakao.presentation.session_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.presentation.session_list.SessionListFilterItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SessionDetailViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _likeList: MutableStateFlow<Set<String>> = MutableStateFlow(emptySet())
    val likeList by lazy {
        _likeList.asStateFlow()
    }
    val session: Session

    init {
        session = savedStateHandle.get<Session>("Session")?: Session()
    }



}


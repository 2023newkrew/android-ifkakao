package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.usecase.LoadLikesUseCase
import com.example.ifkakao.domain.usecase.LoadSessionsUseCase
import com.example.ifkakao.domain.usecase.SaveLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SessionListViewModel
    @Inject constructor(
        val loadLikesUseCase: LoadLikesUseCase,
        val loadSessionsUseCase: LoadSessionsUseCase,
        val saveLikeUseCase: SaveLikeUseCase,
    ): ViewModel() {

        private lateinit var _filterItems: MutableStateFlow<SessionListFilterItems>
        private val filterItems by lazy {
            _filterItems.asStateFlow()
        }

        fun initFilterItems(filterItems: SessionListFilterItems){
            _filterItems = MutableStateFlow(filterItems)
        }


}
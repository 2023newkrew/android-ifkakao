package com.example.ifkakao.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor()
    : ViewModel() {

    private val _mainUiState: MutableStateFlow<MainUiState> =
        MutableStateFlow(
            MainUiState()
        )

    val mainUiState = _mainUiState.asStateFlow()


    fun footerCorpClicked(){
        _mainUiState.value = mainUiState.value.copy(isKakaoCorpClicked = true)
    }


    fun footerLastKakaoClicked(){
        _mainUiState.value = mainUiState.value.copy(isLastIfKakaoClicked = true)
    }
}
package com.example.ifkakao.presentation.home

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.use_case.GetIsDualPaneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getIsDualPaneUseCase: GetIsDualPaneUseCase
) : ViewModel() {
    fun getIsDualPane() = getIsDualPaneUseCase()
}
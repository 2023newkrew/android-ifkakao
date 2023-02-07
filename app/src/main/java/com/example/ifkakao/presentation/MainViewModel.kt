package com.example.ifkakao.presentation

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.use_case.GetIsDualPaneUseCase
import com.example.ifkakao.domain.use_case.GetWidthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsDualPaneUseCase: GetIsDualPaneUseCase,
    private val getWidthUseCase: GetWidthUseCase
) : ViewModel() {
    var backupStatusBarColor: Int? = null

    fun getIsDualPane() = getIsDualPaneUseCase()

    fun getWidth() = getWidthUseCase()
}
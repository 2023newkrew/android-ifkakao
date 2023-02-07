package com.example.ifkakao.presentation

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.use_case.GetIsDualPaneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsDualPaneUseCase: GetIsDualPaneUseCase
) : ViewModel() {
    var backupStatusBarColor: Int? = null

    fun getIsDualPane() = getIsDualPaneUseCase()
}
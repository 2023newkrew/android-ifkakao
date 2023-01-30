package com.example.ifkakao.presentation.list

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.use_case.GetSessionInfoListUseCase
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val getSessionInfoListUseCase: GetSessionInfoListUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}
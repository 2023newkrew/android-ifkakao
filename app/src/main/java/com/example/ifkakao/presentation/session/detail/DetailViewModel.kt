package com.example.ifkakao.presentation.session.detail

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.model.Info
import com.example.ifkakao.domain.use_case.GetWidthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWidthUseCase: GetWidthUseCase
) : ViewModel() {
    var info: Info? = null

    fun getWidth() = getWidthUseCase()
}
package com.example.ifkakao.presentation.session.detail

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ifkakao.ARG_KEY_INFO
import com.example.ifkakao.domain.model.Info
import com.example.ifkakao.domain.use_case.GetWidthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWidthUseCase: GetWidthUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    init {
        savedStateHandle.get<Parcelable>(ARG_KEY_INFO)?.let {
            info = it as Info
        }
    }

    var info: Info? = null

    fun getWidth() = getWidthUseCase()
}
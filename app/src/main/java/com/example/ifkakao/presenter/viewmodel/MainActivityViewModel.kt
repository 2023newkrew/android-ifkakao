package com.example.ifkakao.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.repository.SessionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {
    fun load() {
        viewModelScope.launch {
            val testLog = sessionRepository.getSessions()
            Log.d("test", testLog.toString())
        }
    }
}
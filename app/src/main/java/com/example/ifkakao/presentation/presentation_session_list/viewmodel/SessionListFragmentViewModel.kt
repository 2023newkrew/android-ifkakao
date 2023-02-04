package com.example.ifkakao.presentation.presentation_session_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.repository.SessionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListFragmentViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {

    fun load() {
        viewModelScope.launch {
            val testLog = sessionRepository.getSessions()
            println("allen : " + testLog.toString())
        }
    }
}
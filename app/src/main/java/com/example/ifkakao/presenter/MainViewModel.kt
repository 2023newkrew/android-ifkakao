package com.example.ifkakao.presenter

import android.util.Log
import com.example.ifkakao.domain.repository.SessionRepository
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {
    fun load(){
        viewModelScope.launch{
            val testLog = sessionRepository.getSessions()
            Log.d("test",testLog.toString())
        }
    }
}
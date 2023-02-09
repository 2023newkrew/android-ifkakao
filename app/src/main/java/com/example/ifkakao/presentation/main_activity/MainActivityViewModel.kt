package com.example.ifkakao.presentation.main_activity

import androidx.lifecycle.ViewModel
import com.example.ifkakao.domain.repository.SessionRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val sessionRepository: SessionRepository) : ViewModel() {

}
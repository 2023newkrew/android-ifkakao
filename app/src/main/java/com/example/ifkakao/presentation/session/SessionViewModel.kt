package com.example.ifkakao.presentation.session

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.data.data_source.mapper.toInfo
import com.example.ifkakao.domain.model.Info
import com.example.ifkakao.domain.use_case.GetSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val application: Application,
    private val getSessionsUseCase: GetSessionsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SessionState())
    val state = _state.asStateFlow()

    val infoList =
        mutableListOf<Info>().apply {
            viewModelScope.launch {
                getSessionsUseCase().map {
                    add(it.toInfo())
                }
            }
        }

    fun filterInfoListByType(type: String) {
        val typeSet = hashSetOf<String>().apply {
            addAll(state.value.typeSet)
            if (contains(type)) remove(type)
            else add(type)
        }
        _state.value = state.value.copy(
            typeSet = typeSet
        )
        filterInfoList()
    }

    fun filterInfoListByTrack(track: String) {
        val trackSet = hashSetOf<String>().apply {
            addAll(state.value.trackSet)
            if (contains(track)) remove(track)
            else add(track)
        }
        _state.value = state.value.copy(
            trackSet = trackSet
        )
        filterInfoList()
    }

    fun filterInfoListByCompany(company: String) {
        val companySet = hashSetOf<String>().apply {
            addAll(state.value.companySet)
            if (contains(company)) remove(company)
            else add(company)
        }
        _state.value = state.value.copy(
            companySet = companySet
        )
        filterInfoList()
    }

    private fun filterInfoList() {
        val filteredInfoList = mutableListOf<Info>()
            .apply { addAll(infoList) }
            .filter { state.value.typeSet.contains(it.sessionType) }
            .filter { state.value.trackSet.contains(it.track) }
            .filter { state.value.companySet.contains(it.company) }
        _state.value = state.value.copy(
            filteredInfoList = filteredInfoList
        )
    }
}

data class SessionState(
    val typeSet: Set<String> = setOf(),
    val trackSet: Set<String> = setOf(),
    val companySet: Set<String> = setOf(),
    val filteredInfoList: List<Info> = listOf()
)
package com.example.ifkakao.presentation.session.select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.*
import com.example.ifkakao.data.data_source.mapper.toInfo
import com.example.ifkakao.domain.model.Info
import com.example.ifkakao.domain.use_case.GetIsDualPaneUseCase
import com.example.ifkakao.domain.use_case.GetLikeUseCase
import com.example.ifkakao.domain.use_case.GetSessionsUseCase
import com.example.ifkakao.domain.use_case.PutLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionSelectViewModel @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val getLikeUseCase: GetLikeUseCase,
    private val putLikeUseCase: PutLikeUseCase,
    private val getIsDualPaneUseCase: GetIsDualPaneUseCase,
    private var savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(SessionState())
    val state = _state.asStateFlow()

    private val _foldState = MutableStateFlow(FoldState())
    val foldState = _foldState.asStateFlow()

    private val infoList = mutableListOf<Info>()

    init {
        savedStateHandle.get<String>(ARG_KEY_TYPE)?.let { filterInfoListByType(it) }
        savedStateHandle.get<String>(ARG_KEY_TRACK)?.let { filterInfoListByTrack(it) }
    }

    fun loadInfoList() = viewModelScope.launch {
        if (infoList.isEmpty()) {
            getSessionsUseCase().map { session ->
                infoList.add(session.toInfo().apply { like = getLikeUseCase(id) })
            }
        }

        filterInfoList()
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

    fun filterInfoListByLike(like: String) {
        val likeSet = hashSetOf<String>().apply {
            addAll(state.value.likeSet)
            if (contains(like)) remove(like)
            else add(like)
        }
        _state.value = state.value.copy(
            likeSet = likeSet
        )
        filterInfoList()
    }

    fun filterInfoListByDay(day: Int) {
        _state.value = state.value.copy(
            day = day
        )
        filterInfoList()
    }

    fun resetFilter() {
        _state.value = state.value.copy(
            typeSet = setOf(),
            trackSet = setOf(),
            companySet = setOf(),
            likeSet = setOf()
        )
        filterInfoList()
    }

    fun toggleLike(id: String) = viewModelScope.launch {
        val info = infoList.first { it.id == id }
        val index = infoList.indexOf(info)

        val newInfo = info.copy(
            like = !info.like
        )
        putLikeUseCase(id, newInfo.like)
        infoList[index] = newInfo

        filterInfoList()
    }

    private fun filterInfoList() {
        val filteredInfoList = mutableListOf<Info>()
            .apply { addAll(infoList) }
            .asSequence()
            .filter { state.value.typeSet.isEmpty() || state.value.typeSet.contains(it.sessionType) }
            .filter {
                state.value.trackSet.isEmpty() || state.value.trackSet.intersect(it.track)
                    .isNotEmpty()
            }
            .filter { state.value.companySet.isEmpty() || state.value.companySet.contains(it.company) }
            .filter { state.value.likeSet.isEmpty() || it.like }
            .filter { state.value.day == 0 || state.value.day == it.sessionDay }
            .toList()
        _state.value = state.value.copy(
            filteredInfoList = filteredInfoList
        )
    }

    fun toggleFold(filterCode: Int) {
        _foldState.value = foldState.value.copy(
            isTypeFolded =
            if (filterCode == FILTER_CODE_TYPE) !foldState.value.isTypeFolded
            else foldState.value.isTypeFolded,
            isTrackFolded =
            if (filterCode == FILTER_CODE_TRACK) !foldState.value.isTrackFolded
            else foldState.value.isTrackFolded,
            isCompanyFolded =
            if (filterCode == FILTER_CODE_COMPANY) !foldState.value.isCompanyFolded
            else foldState.value.isCompanyFolded,
            isLikeFolded =
            if (filterCode == FILTER_CODE_LIKE) !foldState.value.isLikeFolded
            else foldState.value.isLikeFolded,
        )
    }

    fun getIsDualPane() = getIsDualPaneUseCase()
}

data class SessionState(
    val typeSet: Set<String> = setOf(),
    val trackSet: Set<String> = setOf(),
    val companySet: Set<String> = setOf(),
    val likeSet: Set<String> = setOf(),
    val day: Int = 0,
    val filteredInfoList: List<Info> = listOf()
)

data class FoldState(
    val isTypeFolded: Boolean = false,
    val isTrackFolded: Boolean = false,
    val isCompanyFolded: Boolean = false,
    val isLikeFolded: Boolean = false
)
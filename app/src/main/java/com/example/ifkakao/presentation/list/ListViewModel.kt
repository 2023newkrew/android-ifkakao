package com.example.ifkakao.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.*
import com.example.ifkakao.domain.usecase.GetSessionInfoListUseCase
import com.example.ifkakao.domain.usecase.bundle.LikeUseCaseBundle
import com.example.ifkakao.util.ApiError
import com.example.ifkakao.util.ApiException
import com.example.ifkakao.util.ApiSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getSessionInfoListUseCase: GetSessionInfoListUseCase,
    private val likeUseCaseBundle: LikeUseCaseBundle,
) : ViewModel() {

    private lateinit var likeList: List<Like>
    private val _sessionState = MutableStateFlow(SessionState())
    val sessionState = _sessionState.asStateFlow()

    init {
        viewModelScope.launch {
            getSessions()
        }
    }

    fun getLikes(): Flow<List<Like>> =
        likeUseCaseBundle.getLikeListUseCase()

    fun getSessions() {
        viewModelScope.launch {
            val result = getSessionInfoListUseCase(
                likeList = getLikes().first(),
                track = sessionState.value.tracks,
                types = sessionState.value.types,
                company = sessionState.value.companies,
            )
            when (result) {
                is ApiSuccess -> {
                    _sessionState.value = sessionState.value.copy(
                        sessionList = result.data
                    )
                }
                is ApiError -> {
                    println("*************ERROR*************")
                }
                is ApiException -> {
                    println(result.e)
                    throw result.e
                }
                else -> {
                    println("*************Else*************")
                }
            }
        }

    }

    // 좋아요 확장 함수
    fun SessionInfo.like() = viewModelScope.launch {
        likeUseCaseBundle.insertLikeUseCase(Like(id))
        isLiked = true
    }

    fun SessionInfo.unlike() = viewModelScope.launch {
        likeUseCaseBundle.deleteLikeUseCase(Like(id))
        isLiked = false
    }

}

data class SessionState(
    val types: Set<SessionType> = setOf(),
    val tracks: Set<Track> = setOf(),
    val companies: Set<Company> = setOf(),
    val day: SessionDay? = null,
    val sessionList: List<SessionInfo> = mutableListOf()
)
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

    private val _sessionData = MutableStateFlow(SessionData())
    val sessionData = _sessionData.asStateFlow()

    init {
//        viewModelScope.launch {
//            getSessions()
//        }
    }

    fun getLikes(): Flow<List<Like>> =
        likeUseCaseBundle.getLikeListUseCase()

    fun getSessions() {
        viewModelScope.launch {
            val result = getSessionInfoListUseCase(
                likeList = getLikes().first(),
                tracks = sessionState.value.tracks,
                types = sessionState.value.types,
                companies = sessionState.value.companies,
                day = sessionState.value.day,
            )
            when (result) {
                is ApiSuccess -> {
                    _sessionData.value = sessionData.value.copy(
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

    fun setSessionDay(day: SessionDay) {
        _sessionState.value = sessionState.value.copy(
            day = day
        )
    }

    fun checkType(sessionType: SessionType) {
        _sessionState.value = sessionState.value.copy(
            types = sessionState.value.types.plus(sessionType)
        )
    }

    fun unCheckType(sessionType: SessionType) {
        _sessionState.value = sessionState.value.copy(
            types = sessionState.value.types.minus(sessionType)
        )
    }

    fun checkTrack(track: Track) {
        _sessionState.value = sessionState.value.copy(
            tracks = sessionState.value.tracks.plus(track)
        )
    }

    fun unCheckTrack(track: Track) {
        _sessionState.value = sessionState.value.copy(
            tracks = sessionState.value.tracks.minus(track)
        )
    }

    fun checkCompany(company: Company) {
        _sessionState.value = sessionState.value.copy(
            companies = sessionState.value.companies.plus(company)
        )
    }

    fun unCheckCompany(company: Company) {
        _sessionState.value = sessionState.value.copy(
            companies = sessionState.value.companies.minus(company)
        )
    }

    fun resetFilter() {
        _sessionState.value = sessionState.value.copy(
            types = setOf(),
            tracks = setOf(),
            companies = setOf(),
            day = SessionDay.Null,
        )
    }

    // 좋아요 확장 함수
    fun sessionLike(sessionInfo: SessionInfo) = viewModelScope.launch {
        likeUseCaseBundle.insertLikeUseCase(Like(sessionInfo.id))
    }

    fun sessionUnLike(sessionInfo: SessionInfo) = viewModelScope.launch {
        likeUseCaseBundle.deleteLikeUseCase(Like(sessionInfo.id))
    }

}

data class SessionState(
    val types: Set<SessionType> = setOf(),
    val tracks: Set<Track> = setOf(),
    val companies: Set<Company> = setOf(),
    val day: SessionDay = SessionDay.Null,
)

data class SessionData(
    val sessionList: List<SessionInfo> = mutableListOf()
)
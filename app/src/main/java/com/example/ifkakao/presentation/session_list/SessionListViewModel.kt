package com.example.ifkakao.presentation.session_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.model.isFilter
import com.example.ifkakao.domain.usecase.LoadLikesUseCase
import com.example.ifkakao.domain.usecase.LoadSessionsUseCase
import com.example.ifkakao.domain.usecase.SaveLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class SessionListViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    val loadLikesUseCase: LoadLikesUseCase,
    val loadSessionsUseCase: LoadSessionsUseCase,
    val saveLikeUseCase: SaveLikeUseCase,
) : ViewModel() {

    private val _filterItems: MutableStateFlow<SessionListFilterItems> =
        MutableStateFlow(SessionListFilterItems())
    val filterItems by lazy {
        _filterItems.asStateFlow()
    }

    private val _showSessionList: MutableStateFlow<List<Session>> = MutableStateFlow(emptyList())
    val showSessionList by lazy {
        _showSessionList.asStateFlow()
    }

    private val _likeList: MutableStateFlow<Set<String>> = MutableStateFlow(emptySet())
    val likeList by lazy {
        _likeList.asStateFlow()
    }

    private lateinit var sessionList: List<Session>

    init {
        savedStateHandle.get<SessionListFilterItems>("FilterItems")?.let {
            _filterItems.value = it
        } ?: run {
            _filterItems.value = SessionListFilterItems()
        }

        viewModelScope.launch {
            _likeList.value = loadLikesUseCase()
            sessionList = loadSessionsUseCase()

            _showSessionList.value = sessionList.filter {
                it.isFilter(
                    filterItems.value,
                    _likeList.value
                )
            }.map {
                if (_likeList.value.contains(it.id.toString()))
                    it.copy(isLike = true)
                else
                    it
            }
        }
    }


    fun likeToggle(id: Int) {
        val set = _likeList.value.toMutableSet()
        if (id.toString() in set) {
            set.remove(id.toString())
            _showSessionList.value = showSessionList.value.map {
                if (it.id == id) {
                    it.copy(isLike = false)
                } else
                    it
            }
        } else {
            set.add(id.toString())
            _showSessionList.value = showSessionList.value.map {
                if (it.id == id) {
                    it.copy(isLike = true)
                } else
                    it
            }
        }
        _likeList.value = set
        saveLikeUseCase(set)
    }

    fun dateSelected(num: Int) {
        when (num) {
            0 -> _filterItems.value = filterItems.value.copy(
                isDateOne = false,
                isDateTwo = false,
                isDateThree = false
            )
            1 -> _filterItems.value = filterItems.value.copy(
                isDateOne = true,
                isDateTwo = false,
                isDateThree = false
            )
            2 -> _filterItems.value = filterItems.value.copy(
                isDateOne = false,
                isDateTwo = true,
                isDateThree = false
            )
            3 -> _filterItems.value = filterItems.value.copy(
                isDateOne = false,
                isDateTwo = false,
                isDateThree = true
            )
        }
        _showSessionList.value = sessionList.filter {
            it.isFilter(
                filterItems.value,
                _likeList.value
            )
        }.map {
            if (_likeList.value.contains(it.id.toString()))
                it.copy(isLike = true)
            else
                it
        }
    }

    fun filterItemChanged(id: Int, value: Boolean) {
        when (id) {
            0 -> _filterItems.value = filterItems.value.copy(isKeynote = value)
            1 -> _filterItems.value = filterItems.value.copy(isPreview = value)
            2 -> _filterItems.value = filterItems.value.copy(isTechSession = value)
            3 -> _filterItems.value = filterItems.value.copy(is1015 = value)
            4 -> _filterItems.value = filterItems.value.copy(isAi = value)
            5 -> _filterItems.value = filterItems.value.copy(isBe = value)
            6 -> _filterItems.value = filterItems.value.copy(isFe = value)
            7 -> _filterItems.value = filterItems.value.copy(isMobile = value)
            8 -> _filterItems.value = filterItems.value.copy(isCloud = value)
            9 -> _filterItems.value = filterItems.value.copy(isBigData = value)
            10 -> _filterItems.value = filterItems.value.copy(isBlockChain = value)
            11 -> _filterItems.value = filterItems.value.copy(isDevOps = value)
            12 -> _filterItems.value = filterItems.value.copy(isESG = value)
            13 -> _filterItems.value = filterItems.value.copy(isGeneral = value)
            14 -> _filterItems.value = filterItems.value.copy(isCulture = value)
            15 -> _filterItems.value = filterItems.value.copy(isKakao = value)
            16 -> _filterItems.value = filterItems.value.copy(isKakaoPay = value)
            17 -> _filterItems.value = filterItems.value.copy(isKakaoEnterprise = value)
            18 -> _filterItems.value = filterItems.value.copy(isKakaoMobility = value)
            19 -> _filterItems.value = filterItems.value.copy(isKakaoBank = value)
            20 -> _filterItems.value = filterItems.value.copy(isKakaoBrain = value)
            21 -> _filterItems.value = filterItems.value.copy(isKakaoGames = value)
            22 -> _filterItems.value = filterItems.value.copy(isKakaoEntertainment = value)
            23 -> _filterItems.value = filterItems.value.copy(isKrustUniverse = value)
            24 -> _filterItems.value = filterItems.value.copy(isKakaoPickoma = value)
        }
        viewModelScope.launch {
            _showSessionList.value = sessionList.filter {
                it.isFilter(
                    filterItems.value,
                    _likeList.value
                )
            }
        }

    }

    fun filterReset(){
        for (i in 0..24){
            filterItemChanged(i,false)
        }
    }
}
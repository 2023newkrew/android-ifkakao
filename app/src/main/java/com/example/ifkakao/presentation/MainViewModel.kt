package com.example.ifkakao.presentation

import androidx.lifecycle.ViewModel
import com.example.ifkakao.data.source.remote.WorldTimeApi
import com.example.ifkakao.domain.model.Timezone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val worldTimeApi: WorldTimeApi
) : ViewModel() {

    fun observeTimezone(): Flow<Timezone> = callbackFlow {
        try {
            while (true) {
                send(worldTimeApi.getWorldTime())
                delay(3000)
            }
        } catch (e: Exception) {
            close()
        }
    }
}
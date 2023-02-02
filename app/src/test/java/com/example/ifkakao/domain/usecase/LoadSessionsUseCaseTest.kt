package com.example.ifkakao.domain.usecase

import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import com.example.ifkakao.domain.repository.RemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test

object MockRemoteRepository : RemoteRepository {
    override suspend fun loadSessions(): List<ResultSession> {
        return listOf(ResultSession(), ResultSession(id = 1))
    }
}


class LoadSessionsUseCaseTest {

    @Test
    operator fun invoke() = runBlocking{
        val loadSessionsUseCase = LoadSessionsUseCase(MockRemoteRepository)
        assertEquals(loadSessionsUseCase(), listOf(ResultSession(), ResultSession(id =1)))
    }
}
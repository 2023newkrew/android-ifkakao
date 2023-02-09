package com.example.ifkakao.data.repository

import com.example.ifkakao.data.data_source.remote.SessionService
import com.example.ifkakao.data.data_source.remote.dto.ResultSession
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test

object MockSessionService1 : SessionService {
    override suspend fun getIfKakaoSessions(accept: String): List<ResultSession> {
        return listOf(
            ResultSession(),
            ResultSession(id = 1),
            ResultSession(id = 2)
        )
    }
}

object MockSessionService2 : SessionService {
    override suspend fun getIfKakaoSessions(accept: String): List<ResultSession> {
        return emptyList()
    }
}


class RemoteRepositoryImplTest {
    @Test
    fun loadSessions() = runBlocking {
        val mockSessionService1 = MockSessionService1
        val mockSessionService2 = MockSessionService2

        val result1 = mockSessionService1.getIfKakaoSessions()
        assertEquals(result1[0], ResultSession())
        assertEquals(result1.size, 3)

        val result2 = mockSessionService2.getIfKakaoSessions()
        assertEquals(result2.size,0)
    }
}
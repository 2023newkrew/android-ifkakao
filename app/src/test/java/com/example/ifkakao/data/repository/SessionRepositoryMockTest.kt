package com.example.ifkakao.data.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import java.util.*

class SessionRepositoryMockTest {

    private val sessionRepositoryMock = SessionRepositoryMock()

    @Test
    fun getAllSessions() = runBlocking {
        val sessions = sessionRepositoryMock.getAllSessions()
        assertEquals(125, sessions.size)
        assertEquals(true, sessions.all { it.id > 0 })

        assertEquals(4, sessions.filter { it.sessionDay == 1 }.size)
        assertEquals(55, sessions.filter { it.sessionDay == 2 }.size)
        assertEquals(66, sessions.filter { it.sessionDay == 3 }.size)

    }
}
package com.example.ifkakao.data.repository

import com.example.ifkakao.data.data_source.local.SessionLikeDao
import com.example.ifkakao.domain.model.Session
import com.example.ifkakao.domain.model.SessionLike
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import java.util.*

object SessionLikeDaoMock : SessionLikeDao {

    private val sessionLikes = mutableSetOf<SessionLike>()
    override suspend fun getSessionLikes(): List<SessionLike> {

        return sessionLikes.toList()
    }

    override suspend fun isSessionLike(likeSessionId: Int): SessionLike? {
        return sessionLikes.firstOrNull { it.likeSessionId == likeSessionId }
    }

    private var counter: Int = 1
    override suspend fun insertSessionLike(likeSessionId: Int) {
        if (sessionLikes.any { it.likeSessionId == likeSessionId }) return
        sessionLikes.add(SessionLike(id = counter++, likeSessionId = likeSessionId))
    }

    override suspend fun deleteSessionLike(likeSessionId: Int) {
        sessionLikes.removeAll { it.likeSessionId == likeSessionId }
    }


}

@Suppress("NonAsciiCharacters")
class SessionRepositoryMockTest {

    private val sessionRepositoryMock = SessionRepositoryMock(SessionLikeDaoMock)

    @Test
    fun `좋아요 정보가 세션에 잘 mapping 된다`() = runBlocking {
        var sessions = sessionRepositoryMock.getAllSessions()
        assertEquals(false, sessions.first { it.id == 110 }.isLike)
        assertEquals(false, sessions.first { it.id == 111 }.isLike)
        assertEquals(false, sessions.first { it.id == 112 }.isLike)

        sessionRepositoryMock.likeSession(sessions.first { it.id == 110 })
        sessionRepositoryMock.likeSession(sessions.first { it.id == 111 })

        sessions = sessionRepositoryMock.getAllSessions()
        assertEquals(true, sessions.first { it.id == 110 }.isLike)
        assertEquals(true, sessions.first { it.id == 111 }.isLike)
        assertEquals(false, sessions.first { it.id == 112 }.isLike)

        sessionRepositoryMock.unlikeSession(sessions.first { it.id == 110 })
        sessionRepositoryMock.unlikeSession(sessions.first { it.id == 111 })

        sessions = sessionRepositoryMock.getAllSessions()
        assertEquals(false, sessions.first { it.id == 110 }.isLike)
        assertEquals(false, sessions.first { it.id == 111 }.isLike)
        assertEquals(false, sessions.first { it.id == 112 }.isLike)
    }

    @Test
    fun getAllSessions() = runBlocking {
        val sessions = sessionRepositoryMock.getAllSessions()
        assertEquals(125, sessions.size)
        assertEquals(true, sessions.all { it.id > 0 })
        assertEquals(125, sessions.distinctBy { it.id }.size)

        assertEquals(4, sessions.filter { it.sessionDay == 1 }.size)
        assertEquals(55, sessions.filter { it.sessionDay == 2 }.size)
        assertEquals(66, sessions.filter { it.sessionDay == 3 }.size)

        println(sessions[0])
    }
}
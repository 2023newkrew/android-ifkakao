package com.example.ifkakao.domain.usecase

import com.example.ifkakao.data.repository.SessionLikeDaoMock
import com.example.ifkakao.data.repository.SessionRepositoryMock
import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import com.example.ifkakao.presentation.session_list.SessionFilter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test

@Suppress("NonAsciiCharacters")
class GetSessionsUseCaseTest {
    private val sessionRepository = SessionRepositoryMock(SessionLikeDaoMock)
    private val getSessionUseCase = GetSessionsUseCase(sessionRepository)

    @Test
    fun `좋아요 필터링 테스트`() = runBlocking {
        var sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter(),
            showLikeOnly = false
        )
        assertEquals(125, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter(),
            showLikeOnly = true
        )
        assertEquals(0, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter(),
            showLikeOnly = false
        )
        sessionRepository.likeSession(sessions[0])
        sessionRepository.likeSession(sessions[1])

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter(),
            showLikeOnly = true
        )
        assertEquals(2, sessions.size)
    }

    @Test
    fun `트랙 필터링 테스트`() = runBlocking {
        var sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                tracks = listOf(Track.Ai, Track.DevOps)
            ),
            showLikeOnly = false
        )
        assertEquals(31, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                tracks = listOf(Track.Mobile)
            ),
            showLikeOnly = false
        )
        assertEquals(8, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                tracks = listOf(Track.Re1015, Track.Ai, Track.BackEnd)
            ),
            showLikeOnly = false
        )
        assertEquals(51, sessions.size)
    }

    @Test
    fun `날짜별 필터링 테스트`() = runBlocking {
        var sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter(),
            showLikeOnly = false
        )
        assertEquals(125, sessions.size)
        sessions = getSessionUseCase(
            sessionDay = 1,
            sessionFilter = SessionFilter(),
            showLikeOnly = false
        )
        assertEquals(4, sessions.size)
        sessions = getSessionUseCase(
            sessionDay = 2,
            sessionFilter = SessionFilter(),
            showLikeOnly = false
        )
        assertEquals(55, sessions.size)
        sessions = getSessionUseCase(
            sessionDay = 3,
            sessionFilter = SessionFilter(),
            showLikeOnly = false
        )
        assertEquals(66, sessions.size)
    }

    @Test
    fun `유형 필터링 테스트`() = runBlocking {
        var sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                sessionTypes = listOf(SessionType.KeyNote)
            ),
            showLikeOnly = false
        )
        assertEquals(8, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                sessionTypes = listOf(SessionType.Preview)
            ),
            showLikeOnly = false
        )
        assertEquals(11, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                sessionTypes = listOf(SessionType.Preview, SessionType.KeyNote)
            ),
            showLikeOnly = false
        )
        assertEquals(19, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                sessionTypes = listOf(SessionType.TechSession)
            ),
            showLikeOnly = false
        )
        assertEquals(106, sessions.size)

    }

    @Test
    fun `회사 필터링 테스트`() = runBlocking {
        var sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.KakaoEnterPrise)
            ),
            showLikeOnly = false
        )
        assertEquals(16, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.Kakao)
            ),
            showLikeOnly = false
        )
        assertEquals(50, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.Kakao, Company.KakaoEnterPrise)
            ),
            showLikeOnly = false
        )
        assertEquals(66, sessions.size)
    }

    @Test
    fun `일자, 트랙, 회사, 유형 종합 필터링 테스트`() = runBlocking {
        var sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.Kakao),
                sessionTypes = listOf(SessionType.TechSession)
            ),
            showLikeOnly = false
        )
        assertEquals(42, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.Kakao, Company.KakaoEnterPrise),
                sessionTypes = listOf(SessionType.TechSession, SessionType.Preview)
            ),
            showLikeOnly = false
        )
        assertEquals(61, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 0,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.Kakao, Company.KakaoEnterPrise),
                sessionTypes = listOf(SessionType.TechSession, SessionType.Preview),
                tracks = listOf(Track.BackEnd)
            ),
            showLikeOnly = false
        )
        assertEquals(11, sessions.size)

        sessions = getSessionUseCase(
            sessionDay = 3,
            sessionFilter = SessionFilter().copy(
                companies = listOf(Company.Kakao, Company.KakaoEnterPrise),
                sessionTypes = listOf(SessionType.TechSession, SessionType.Preview),
                tracks = listOf(Track.BackEnd)
            ),
            showLikeOnly = false
        )
        assertEquals(3, sessions.size)
    }


}
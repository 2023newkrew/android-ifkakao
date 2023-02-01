package com.example.ifkakao.domain.model

import org.junit.Assert.*
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Suppress("NonAsciiCharacters", "SpellCheckingInspection")
class SessionTest {


    @Test
    fun `세션 타입, 트랙 문자열이 잘 반환된다`() {
        val session = Session(
            id = 110,
            title = "Our Social Mission",
            description = "남궁훈 카카오 비상대책위원회 재발방지대책 소위원회 위원장이 전하는 If Kakao 오프닝 인사말," +
                    " 카카오의 사회적 소명에 대한 생각, 그리고 새로운 카카오에 대한 각오와 의지를 확인하실 수 있습니다.",
            timeStamp = 1670378400000,
            sessionDay = 1,
            sessionType = SessionType.KeyNote,
            tracks = listOf(Track.BackEnd, Track.Ai),
            company = Company.Kakao,
            sessionVodLink = "https://youtu.be/drVzQXa0Tjc",
            vodThumbUrl = "",
            users = listOf(
                User(
                    id = "nkay.play",
                    intro = "카카오 재발방지대책 소위원회 공동 위원장을 맡고 있는 남궁훈, Nkay입니다.",
                    imageUrl = "https://mk.kakaocdn.net/dn/if-kakao/2022/if@kakao.png"
                )
            ),
            tags = "#ESG #사회적책임 #일이되게하는방식 #조직문화",
            pptUrl = "",
            isLike = false
        )
        assertEquals("키노트 백앤드 AI", session.getTypeAndTracksString())

    }

    @Test
    fun `타임스탬프의 월, 일, 시간이 잘 파싱된다`() {
        val timeStamp = 1670468400000L
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())
        assertEquals(12, date.monthValue)
        assertEquals(8, date.dayOfMonth)
        assertEquals(12, date.hour)
    }
}
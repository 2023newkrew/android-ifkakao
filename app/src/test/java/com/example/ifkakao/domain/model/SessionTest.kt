package com.example.ifkakao.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test
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
        val calendar = Calendar.getInstance()
        calendar.time = Date(timeStamp)
        assertEquals(12, calendar.get(Calendar.MONTH) + 1)
        assertEquals(8, calendar.get(Calendar.DAY_OF_MONTH))
        // assertEquals(12, calendar.get(Calendar.HOUR_OF_DAY))
    }
}
package com.example.ifkakao.data.data_source.remote.mapper

import com.example.ifkakao.data.data_source.remote.dto.SessionDTO
import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import org.junit.Assert.assertEquals
import org.junit.Test

@Suppress("SpellCheckingInspection", "NonAsciiCharacters")
class SeesionMapperKtTest {

    @Test
    fun `dto track 문자열이 잘 파싱된다`() {
        var track = "[\"culture\"]"
        var result = parseTrackList(track)

        assertEquals(1, result.size)
        assertEquals(Track.Culture, result.first())

        track = "[\"\"]"
        result = parseTrackList(track)
        assertEquals(0, result.size)

        track = "[\"ai\",\"devops\"]"
        result = parseTrackList(track)
        assertEquals(2, result.size)
        assertEquals(Track.Ai, result[0])
        assertEquals(Track.DevOps, result[1])
    }

    @Test
    fun toSession() {
        val sessionDTO = SessionDTO(
            user2ImageUrl = "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker%2Fpo.ai@kep.jpg",
            user2Id = "po.ai",
            user3Intro = "",
            user3ImageUrl = "",
            pptUrl = "https://speakerdeck.com/kakao/ifkakao22-68",
            user2Intro = "카카오엔터프라이즈 비전인텔리전스팀의 포 입니다. 얼굴인식을 모바일에 적용하는 일을 하고 있습니다.",
            user1Intro = "카카오엔터프라이즈 비전인텔리전스팀의 앤드루 입니다. 얼굴인식을 모바일에 적용하는 일을 하고 있습니다.",
            sessionDay = "2",
            description = "딥러닝 기반의 얼굴인식 모델을 처리 성능이 빈약한 모바일 및 전용장비에 적용하기 위한 최적화 방법에 대해서 공유하고자 합니다.",
            title = "모바일 얼굴인식 SDK 개발",
            sessionType = "tech",
            liveImageUrl = "",
            user1Id = "andrew.js",
            user1ImageUrl = "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker%2Fandrew.js@kep.jpg",
            id = 68,
            tracks = "[\"ai\"]",
            timeStamp = 1670468400000,
            company = "kep",
            user3Id = "",
            meetUpRegisterLink = "",
            liveQnaUrl = "https://tech.kakao.com/ifkakao2022/?title=%EB%AA%A8%EB%B0%94%EC%9D%BC%20%EC%96%BC%EA%B5%B4%EC%9D%B8%EC%8B%9D%20SDK%20%EA%B0%9C%EB%B0%9C&id=68",
            liveChanelUrl = "",
            sessionVodLink = "https://youtu.be/-kqHg9Z3tto",
            voidThumbUrl = "https://mk.kakaocdn.net/dn/if-kakao/2022/thumb/andrew.js@kep.jpg",
            tags = "#FacialRecognition"
        )
        val session = sessionDTO.toSession()
        assertEquals(68, session.id)
        assertEquals(
            "딥러닝 기반의 얼굴인식 모델을 처리 성능이 빈약한 모바일 및 전용장비에 적용하기 위한 최적화 방법에 대해서 공유하고자 합니다.",
            session.description
        )
        assertEquals(
            "모바일 얼굴인식 SDK 개발", session.title
        )
        assertEquals(
            SessionType.TechSession, session.sessionType
        )
        assertEquals(1, session.tracks.size)
        assertEquals(Track.Ai, session.tracks.first())

        assertEquals(1670468400000, session.timeStamp)
        assertEquals(2, session.sessionDay)
        assertEquals(Company.KakaoEnterPrise, session.company)

        assertEquals("https://youtu.be/-kqHg9Z3tto", session.sessionVodLink)
        assertEquals(
            "https://mk.kakaocdn.net/dn/if-kakao/2022/thumb/andrew.js@kep.jpg", session.vodThumbUrl
        )
        assertEquals("#FacialRecognition", session.tags)


        val users = session.users
        assertEquals(2, users.size)

        assertEquals("andrew.js", users[0].id)
        assertEquals(
            "카카오엔터프라이즈 비전인텔리전스팀의 앤드루 입니다. 얼굴인식을 모바일에 적용하는 일을 하고 있습니다.", users[0].intro
        )
        assertEquals(
            "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker%2Fandrew.js@kep.jpg",
            users[0].imageUrl
        )

        assertEquals(
            "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker%2Fpo.ai@kep.jpg", users[1].imageUrl
        )
        assertEquals("po.ai", users[1].id)
        assertEquals(
            "카카오엔터프라이즈 비전인텔리전스팀의 포 입니다. 얼굴인식을 모바일에 적용하는 일을 하고 있습니다.", users[1].intro
        )

        assertEquals("https://speakerdeck.com/kakao/ifkakao22-68", session.pptUrl)

    }
}
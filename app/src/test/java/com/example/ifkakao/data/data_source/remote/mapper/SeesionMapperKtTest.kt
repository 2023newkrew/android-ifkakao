package com.example.ifkakao.data.data_source.remote.mapper

import com.example.ifkakao.data.data_source.remote.dto.SessionDTO
import com.example.ifkakao.domain.model.Company
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

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
            `6SI_gcz2h1l3` = "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker%2Fpo.ai@kep.jpg",
            C90Y851TGjAd = "po.ai",
            MAjnegS2sUz_ = "",
            eHhghHsGMFV0 = "",
            dc4kWp2OMZBr = "https://speakerdeck.com/kakao/ifkakao22-68",
            `0TNvHfMiEEEE` = "카카오엔터프라이즈 비전인텔리전스팀의 포 입니다. 얼굴인식을 모바일에 적용하는 일을 하고 있습니다.",
            `95fb8cVqD37E` = "카카오엔터프라이즈 비전인텔리전스팀의 앤드루 입니다. 얼굴인식을 모바일에 적용하는 일을 하고 있습니다.",
            tsHALc4rYA5Z = "2",
            pShJsKRFz_mR = "딥러닝 기반의 얼굴인식 모델을 처리 성능이 빈약한 모바일 및 전용장비에 적용하기 위한 최적화 방법에 대해서 공유하고자 합니다.",
            U2G0DHalEQHs = "모바일 얼굴인식 SDK 개발",
            INYXb7hNIfMU = "tech",
            `9yVKHSTkTNmF` = "",
            kwC3iO8Lbj6D = "andrew.js",
            PAifZyJwjcmh = "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker%2Fandrew.js@kep.jpg",
            id = 68,
            GgWcMRm0cNSS = "[\"ai\"]",
            Yk7Sc6yEUma7 = 1670468400000,
            `0xTNIhifz0t7` = "kep",
            b1ERX6ZDBfTy = "",
            WCrhrhpRLki9 = "",
            DvJr7DiMNfhG = "https://tech.kakao.com/ifkakao2022/?title=%EB%AA%A8%EB%B0%94%EC%9D%BC%20%EC%96%BC%EA%B5%B4%EC%9D%B8%EC%8B%9D%20SDK%20%EA%B0%9C%EB%B0%9C&id=68",
            p5Z5M0DP08AF = "",
            I729yvCqaONJ = "https://youtu.be/-kqHg9Z3tto",
            L9WirNOAVF4J = "https://mk.kakaocdn.net/dn/if-kakao/2022/thumb/andrew.js@kep.jpg",
            X_7c2hMWtxne = "#FacialRecognition"
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

        assertEquals(Date(1670468400000), session.date)
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
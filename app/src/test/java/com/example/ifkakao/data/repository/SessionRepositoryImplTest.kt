package com.example.ifkakao.data.repository

import com.example.ifkakao.data.data_source.service.SessionService
import com.example.ifkakao.domain.model.SessionDay
import com.example.ifkakao.domain.model.SessionInfo
import com.example.ifkakao.domain.model.SessionType
import com.example.ifkakao.domain.model.Track
import com.example.ifkakao.util.ApiError
import com.example.ifkakao.util.ApiException
import com.example.ifkakao.util.ApiSuccess
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class SessionRepositoryImplTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(SessionService::class.java)
    private val sessionRepository = SessionRepositoryImpl(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getSessions는 sessionInfo의 형태로 예측한 결과값을 반환한다`() {
        mockWebServer.enqueueResponse("session-200.json", 200)

        val expected = SessionInfo(
            id = 49,
            user1Id = "phoebe.727",
            user1Img = "https://mk.kakaocdn.net/dn/if-kakao/2022/speaker/phoebe.727@krust.jpg",
            user1Intro="크러스트유니버스 코어 데브 팀 어플리케이션 파트에서 개발을 하고 있는 피비입니다. " +
                    "개발자 뿐만 아니라 일반 사용자들도 클레이튼을 쉽게 사용할 수 있도록 돕기 위해 필요한 다양한 개발에 참여하고 있습니다.",
            user2Id="", user2Img="", user2Intro="", user3Id="", user3Img="", user3Intro="",
            title="메타버스에 적합한 블록체인은?: Transaction Latency Measurement",
            description="서비스를 구축하기 위해 어떤 블록체인을 선택할지 고민할 때 성능은 가장 먼저 고려되는 것들 중" +
                    " 하나일 것입니다. 성능지표로는 대표적으로 동작을 수행할 때 걸리는 시간을 나타내는 지연시간(Latency)과" +
                    " 동일 시간 내에 얼마나 많은 동작을 수행할 수 있는지를 나타내는 처리량(Throughput)이 있습니다." +
                    " 처리량을 측정하는 지표인 TPS 는 블록체인의 대표적인 성능 지표로 여겨지는 반면에 지연시간에 대한" +
                    " 논의는 많이 다뤄지지 않습니다. 개개인의 입장에서 생각해보면 처리량보다는 트랜잭션이 처리되는 속도가" +
                    " 더 중요하지 않을까요? 클레이튼은 지연 시간을 알아보기 위해 다양한 블록체인 플랫폼에서 트랜잭션을" +
                    " 네트워크에 전송하고, 확정되기까지의 시간을 측정해 보았습니다. 그리고 그 결과를 기반으로 클레이튼과" +
                    " 다른 블록체인 플랫폼들을 비교해보았습니다.",
            sessionType= SessionType.Tech, sessionDay= SessionDay.DAY_THREE,
            ppt="https://speakerdeck.com/kakao/ifkakao22-49",
            track= listOf(Track.BLOCK_CHAIN),
            sessionDate=1670554800000,
            company="ku", meetupRegisterLink="", liveImgUrl="",
            liveQnaUrl="https://tech.kakao.com/ifkakao2022/?title=%EB%A9%94%ED%83%80%EB%" +
                    "B2%84%EC%8A%A4%EC%97%90%20%EC%A0%81%ED%95%A9%ED%95%9C%20%EB%B8%94%EB%A1" +
                    "%9D%EC%B2%B4%EC%9D%B8%EC%9D%80%3F%3A%20Transaction%20Latency%20Measurem" +
                    "ent&id=49",
            liveChannelUrl="", sessionVodLink="https://youtu.be/dcq-MJB9uXw",
            sessionImg="https://mk.kakaocdn.net/dn/if-kakao/2022/thumb/phoebe.727@krust.jpg",
            tags="#Klaytn #Latencycomparison")

            runTest{
                val result = sessionRepository.getSessions()
                when (result) {
                    is ApiSuccess -> {
                        println("************SUCCESS************")
                        assertEquals(expected, result.data[0])
                    }
                    is ApiError -> {
                        println("************ERROR************")
                        println("code: ${result.code} message: ${result.message}")
                    }
                    is ApiException -> {
                        println("************EXCEPTION************")
                        println("${result.e}")
                    }
                    else -> {
                        println("nothing")
                    }
                }


            }
    }
}

internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}
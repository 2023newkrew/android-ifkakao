package com.example.ifkakao.data.source.remote

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@Suppress("NonAsciiCharacters")
class WorldTimeApiTest {
    private lateinit var api: WorldTimeApi

    private val expected =
        """{"abbreviation":"JST","client_ip":"222.14.27.185","datetime":"2023-01-25T16:15:47.762180+09:00","day_of_week":3,"day_of_year":25,"dst":false,"dst_from":null,"dst_offset":0,"dst_until":null,"raw_offset":32400,"timezone":"Asia/Tokyo","unixtime":1674630947,"utc_datetime":"2023-01-25T07:15:47.762180+00:00","utc_offset":"+09:00","week_number":4}"""

    @Before
    fun setUp() {
        api = WorldTimeApi.create()
    }

    @Test
    fun `통신 테스트`() = runBlocking {
        val timezone = api.getWorldTime()

        Assert.assertEquals(timezone.timezone, "Asia/Tokyo")
    }
}
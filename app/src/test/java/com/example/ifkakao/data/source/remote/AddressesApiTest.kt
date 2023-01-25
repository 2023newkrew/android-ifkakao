package com.example.ifkakao.data.source.remote

import com.example.ifkakao.data.csv.AddressParser
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

@Suppress("NonAsciiCharacters")
class AddressesApiTest {
    private lateinit var api: AddressesApi

    private val expected = """John,Doe,120 jefferson st.,Riverside, NJ, 08075
Jack,McGinnis,220 hobo Av.,Phila, PA,09119
"John ""Da Man""${'"'},Repici,120 Jefferson St.,Riverside, NJ,08075
Stephen,Tyler,"7452 Terrace ""At the Plaza"" road",SomeTown,SD, 91234
,Blankman,,SomeTown, SD, 00298
"Joan ""the bone"", Anne",Jet,"9th, at Terrace plc",Desert City,CO,00123
"""

    @Before
    fun setUp() {
        api = AddressesApi.create()
    }

    @Test
    fun `통신 테스트`() = runBlocking {
        val response = api.getAddresses()

        Assert.assertEquals(response.body().toString(), expected)
    }

    @Test
    fun `통신 및 파싱 테스트`() = runBlocking {
        val response = api.getAddresses()

        val parser = AddressParser()

        val results = parser.parse(response.body()!!)

        Assert.assertEquals("John", results[0].firstName)
        Assert.assertEquals(8075, results[0].number)

        Assert.assertEquals("""John "Da Man"""", results[2].firstName)

        Assert.assertEquals("", results[4].firstName)
    }
}
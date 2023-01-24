package com.example.ifkakao.data.csv

import com.example.ifkakao.domain.model.Address
import org.junit.Assert.*
import org.junit.Test

@Suppress("NonAsciiCharacters")
class AddressParserTest {

    @Test
    fun `Address 파싱 테스트`() {
        val parser = AddressParser()

        val results: List<Address> = parser.parse(
            """John,Doe,120 jefferson st.,Riverside, NJ, 08075
Jack,McGinnis,220 hobo Av.,Phila, PA,09119
"John ""Da Man""${'"'},Repici,120 Jefferson St.,Riverside, NJ,08075
Stephen,Tyler,"7452 Terrace ""At the Plaza"" road",SomeTown,SD, 91234
,Blankman,,SomeTown, SD, 00298
"Joan ""the bone"", Anne",Jet,"9th, at Terrace plc",Desert City,CO,00123
"""
        )

        assertEquals(results[0].firstName, "John")
        assertEquals(results[0].number, 8075)

        assertEquals(results[2].firstName, """John "Da Man"""")

        assertEquals(results[4].firstName, "")
    }
}
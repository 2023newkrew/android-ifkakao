package com.example.ifkakao.data.csv

import com.example.ifkakao.domain.model.Address
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class AddressParser : CsvParser<Address> {

    override fun parse(csvString: String): List<Address> {
        val rows: List<List<String>> = csvReader().readAll(csvString)

        return rows.map { row ->
            Address(
                firstName = row[0],
                lastName = row[1],
                address1 = row[2],
                address2 = row[3],
                address3 = row[4],
                number = row[5].trim().toInt(),
            )
        }
    }
}
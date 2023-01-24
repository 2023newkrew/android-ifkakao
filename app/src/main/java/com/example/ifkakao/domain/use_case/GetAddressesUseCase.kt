package com.example.ifkakao.domain.use_case

import com.example.ifkakao.data.csv.CsvParser
import com.example.ifkakao.data.source.remote.AddressesApi
import com.example.ifkakao.domain.model.Address

class GetAddressesUseCase(
    private val api: AddressesApi,
    private val parser: CsvParser<Address>
) {

    suspend operator fun invoke(): List<Address> {
        val response = api.getAddresses()
        return parser.parse(response.body()!!)
    }
}
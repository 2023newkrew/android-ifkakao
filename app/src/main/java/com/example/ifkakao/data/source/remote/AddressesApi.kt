package com.example.ifkakao.data.source.remote

import retrofit2.Response
import retrofit2.http.GET

interface AddressesApi {

    companion object {
        const val baseUrl = "https://people.sc.fsu.edu/~jburkardt/data/csv/"
    }

    @GET("addresses.csv")
    suspend fun getAddresses(): Response<String>
}
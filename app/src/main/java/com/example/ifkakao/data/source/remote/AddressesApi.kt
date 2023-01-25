package com.example.ifkakao.data.source.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface AddressesApi {

    companion object {
        const val baseUrl = "https://people.sc.fsu.edu/~jburkardt/data/csv/"

        fun create(): AddressesApi = Retrofit.Builder()
            .baseUrl(AddressesApi.baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create()
    }

    @GET("addresses.csv")
    suspend fun getAddresses(): Response<String>
}
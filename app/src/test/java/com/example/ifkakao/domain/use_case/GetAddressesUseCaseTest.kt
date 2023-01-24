package com.example.ifkakao.domain.use_case

import com.example.ifkakao.data.csv.AddressParser
import com.example.ifkakao.data.source.remote.AddressesApi
import com.example.ifkakao.domain.model.Address
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

class GetAddressesUseCaseTest {

    @Test
    fun invoke() = runBlocking {
        val useCase = GetAddressesUseCase(
            api = Retrofit.Builder()
                .baseUrl(AddressesApi.baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(),
            parser = AddressParser(),
        )

        val results: List<Address> = useCase.invoke()

        Assert.assertEquals("John", results[0].firstName)
        Assert.assertEquals(8075, results[0].number)

        Assert.assertEquals("""John "Da Man"""", results[2].firstName)

        Assert.assertEquals("", results[4].firstName)
    }
}

package com.example.a2do_parcial_progra3.data.remote

import com.example.a2do_parcial_progra3.data.remote.dto.ExchangeRateResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FrankfurterApiService {

    @GET("v1/latest")
    suspend fun getRates(
        @Query("base") base: String = "USD",
        @Query("symbols") symbols: String = "EUR"
    ): ExchangeRateResponseDto
}

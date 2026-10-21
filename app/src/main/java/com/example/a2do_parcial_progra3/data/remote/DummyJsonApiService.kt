package com.example.a2do_parcial_progra3.data.remote

import com.example.a2do_parcial_progra3.data.remote.dto.ProductResponseDto
import retrofit2.http.GET

interface DummyJsonApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponseDto
}

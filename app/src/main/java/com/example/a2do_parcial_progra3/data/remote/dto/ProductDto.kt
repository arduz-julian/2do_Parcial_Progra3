package com.example.a2do_parcial_progra3.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductResponseDto(
    @SerializedName("products") val products: List<ProductDto>? = emptyList(),
    @SerializedName("total") val total: Int? = 0,
    @SerializedName("skip") val skip: Int? = 0,
    @SerializedName("limit") val limit: Int? = 0
)

data class ProductDto(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("title") val title: String? = "",
    @SerializedName("description") val description: String? = "",
    @SerializedName("price") val price: Double? = 0.0,
    @SerializedName("category") val category: String? = "",
    @SerializedName("thumbnail") val thumbnail: String? = ""
)

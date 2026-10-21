package com.example.a2do_parcial_progra3.domain.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val priceBOB: Double = price * 6.96,
    val category: String,
    val thumbnail: String
)

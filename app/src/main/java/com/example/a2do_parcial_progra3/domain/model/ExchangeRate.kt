package com.example.a2do_parcial_progra3.domain.model

data class ExchangeRate(
    val currency: String,
    val rate: Double,
    val date: String,
    val baseCurrency: String
)

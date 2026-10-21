package com.example.a2do_parcial_progra3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRateEntity(
    @PrimaryKey val currency: String,
    val rate: Double,
    val date: String,
    val baseCurrency: String
)

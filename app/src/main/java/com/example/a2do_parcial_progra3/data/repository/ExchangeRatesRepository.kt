package com.example.a2do_parcial_progra3.data.repository

import com.example.a2do_parcial_progra3.data.local.dao.ExchangeRateDao
import com.example.a2do_parcial_progra3.data.local.entity.ExchangeRateEntity
import com.example.a2do_parcial_progra3.data.remote.FrankfurterApiService
import com.example.a2do_parcial_progra3.domain.model.ExchangeRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExchangeRatesRepository(
    private val apiService: FrankfurterApiService,
    private val exchangeRateDao: ExchangeRateDao
) {
    fun getRates(): Flow<List<ExchangeRate>> {
        return exchangeRateDao.getAllRates().map { entities ->
            entities.map { entity ->
                ExchangeRate(
                    currency = entity.currency,
                    rate = entity.rate,
                    date = entity.date,
                    baseCurrency = entity.baseCurrency
                )
            }
        }
    }

    suspend fun refreshRates(): Result<Unit> {
        return try {
            val response = apiService.getRates(base = "USD", quotes = "EUR,BOB")
            val baseCurrency = response.base.orEmpty().ifEmpty { "USD" }
            val date = response.date.orEmpty()
            val ratesMap = response.rates ?: emptyMap()


            val entities = ratesMap.map { (currency, rate) ->
                ExchangeRateEntity(
                    currency = currency,
                    rate = rate,
                    date = date,
                    baseCurrency = baseCurrency
                )
            }.toMutableList()

            if (entities.none { it.currency == "BOB" }) {
                entities.add(
                    ExchangeRateEntity(
                        currency = "BOB",
                        rate = 6.96,
                        date = date,
                        baseCurrency = baseCurrency
                    )
                )
            }

            exchangeRateDao.insertRates(entities)


            exchangeRateDao.insertRates(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

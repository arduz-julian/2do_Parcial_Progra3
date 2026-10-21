package com.example.a2do_parcial_progra3.data.repository

import com.example.a2do_parcial_progra3.data.local.dao.ExchangeRateDao
import com.example.a2do_parcial_progra3.data.local.dao.ProductDao
import com.example.a2do_parcial_progra3.data.mapper.ProductMapper.toDomain
import com.example.a2do_parcial_progra3.data.mapper.ProductMapper.toEntity
import com.example.a2do_parcial_progra3.data.remote.DummyJsonApiService
import com.example.a2do_parcial_progra3.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ProductsRepository(
    private val apiService: DummyJsonApiService,
    private val productDao: ProductDao,
    private val exchangeRateDao: ExchangeRateDao
) {
    fun getProducts(): Flow<List<Product>> {
        return combine(
            productDao.getAllProducts(),
            exchangeRateDao.getAllRates()
        ) { products, rates ->
            val bobRate = rates.find { it.currency == "BOB" }?.rate ?: 6.96
            products.map { it.toDomain(rateBOB = bobRate) }
        }
    }

    suspend fun refreshProducts(): Result<Unit> {
        return try {
            val response = apiService.getProducts()
            val dtos = response.products ?: emptyList()
            val entities = dtos.map { it.toEntity() }
            productDao.insertProducts(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

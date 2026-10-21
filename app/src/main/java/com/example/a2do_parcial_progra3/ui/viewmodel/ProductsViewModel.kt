package com.example.a2do_parcial_progra3.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2do_parcial_progra3.data.local.AppDatabase
import com.example.a2do_parcial_progra3.data.remote.RetrofitClient
import com.example.a2do_parcial_progra3.data.repository.ProductsRepository
import com.example.a2do_parcial_progra3.domain.model.Product
import com.example.a2do_parcial_progra3.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = ProductsRepository(
        apiService = RetrofitClient.dummyJsonApiService,
        productDao = database.productDao(),
        exchangeRateDao = database.exchangeRateDao()
    )

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    init {
        observeProducts()
        refresh()
    }

    private fun observeProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                if (products.isNotEmpty()) {
                    _uiState.value = UiState.Success(products)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            if (_uiState.value !is UiState.Success) {
                _uiState.value = UiState.Loading
            }
            val result = repository.refreshProducts()
            result.onFailure { error ->
                if (_uiState.value !is UiState.Success) {
                    _uiState.value = UiState.Error(error.localizedMessage ?: "Error al cargar productos")
                }
            }
        }
    }
}

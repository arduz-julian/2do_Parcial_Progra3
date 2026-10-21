package com.example.a2do_parcial_progra3.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2do_parcial_progra3.data.local.AppDatabase
import com.example.a2do_parcial_progra3.data.remote.RetrofitClient
import com.example.a2do_parcial_progra3.data.repository.ExchangeRatesRepository
import com.example.a2do_parcial_progra3.domain.model.ExchangeRate
import com.example.a2do_parcial_progra3.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = ExchangeRatesRepository(
        apiService = RetrofitClient.frankfurterApiService,
        exchangeRateDao = database.exchangeRateDao()
    )

    private val _uiState = MutableStateFlow<UiState<List<ExchangeRate>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ExchangeRate>>> = _uiState.asStateFlow()

    init {
        observeRates()
        refresh()
    }

    private fun observeRates() {
        viewModelScope.launch {
            repository.getRates().collect { rates ->
                if (rates.isNotEmpty()) {
                    _uiState.value = UiState.Success(rates)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            if (_uiState.value !is UiState.Success) {
                _uiState.value = UiState.Loading
            }
            val result = repository.refreshRates()
            result.onFailure { error ->
                if (_uiState.value !is UiState.Success) {
                    _uiState.value = UiState.Error(error.localizedMessage ?: "Error al cargar tasas de cambio")
                }
            }
        }
    }
}

package com.example.a2do_parcial_progra3.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2do_parcial_progra3.data.local.AppDatabase
import com.example.a2do_parcial_progra3.data.remote.RetrofitClient
import com.example.a2do_parcial_progra3.data.repository.PostsRepository
import com.example.a2do_parcial_progra3.domain.model.Post
import com.example.a2do_parcial_progra3.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = PostsRepository(
        apiService = RetrofitClient.jsonPlaceholderApiService,
        postDao = database.postDao()
    )

    private val _uiState = MutableStateFlow<UiState<List<Post>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Post>>> = _uiState.asStateFlow()

    init {
        observePosts()
        refresh()
    }

    private fun observePosts() {
        viewModelScope.launch {
            repository.getPosts().collect { posts ->
                if (posts.isNotEmpty()) {
                    _uiState.value = UiState.Success(posts)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            if (_uiState.value !is UiState.Success) {
                _uiState.value = UiState.Loading
            }
            val result = repository.refreshPosts()
            result.onFailure { error ->
                if (_uiState.value !is UiState.Success) {
                    _uiState.value = UiState.Error(error.localizedMessage ?: "Error al cargar publicaciones")
                }
            }
        }
    }
}

package com.example.a2do_parcial_progra3.data.repository

import com.example.a2do_parcial_progra3.data.local.dao.PostDao
import com.example.a2do_parcial_progra3.data.mapper.PostMapper.toDomain
import com.example.a2do_parcial_progra3.data.mapper.PostMapper.toEntity
import com.example.a2do_parcial_progra3.data.remote.JsonPlaceholderApiService
import com.example.a2do_parcial_progra3.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostsRepository(
    private val apiService: JsonPlaceholderApiService,
    private val postDao: PostDao
) {
    fun getPosts(): Flow<List<Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun refreshPosts(): Result<Unit> {
        return try {
            val dtos = apiService.getPosts()
            val entities = dtos.map { it.toEntity() }
            postDao.insertPosts(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

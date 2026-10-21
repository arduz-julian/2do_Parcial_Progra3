package com.example.a2do_parcial_progra3.data.remote

import com.example.a2do_parcial_progra3.data.remote.dto.PostDto
import retrofit2.http.GET

interface JsonPlaceholderApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}

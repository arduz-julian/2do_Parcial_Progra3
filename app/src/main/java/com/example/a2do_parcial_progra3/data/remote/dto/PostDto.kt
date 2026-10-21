package com.example.a2do_parcial_progra3.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("userId") val userId: Int? = 0,
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("title") val title: String? = "",
    @SerializedName("body") val body: String? = ""
)

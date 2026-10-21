package com.example.a2do_parcial_progra3.data.mapper

import com.example.a2do_parcial_progra3.data.local.entity.PostEntity
import com.example.a2do_parcial_progra3.data.remote.dto.PostDto
import com.example.a2do_parcial_progra3.domain.model.Post

object PostMapper {

    fun PostDto.toEntity(): PostEntity {
        return PostEntity(
            id = id ?: 0,
            userId = userId ?: 0,
            title = title.orEmpty(),
            body = body.orEmpty()
        )
    }

    fun PostEntity.toDomain(): Post {
        return Post(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }

    fun PostDto.toDomain(): Post {
        return Post(
            id = id ?: 0,
            userId = userId ?: 0,
            title = title.orEmpty(),
            body = body.orEmpty()
        )
    }
}

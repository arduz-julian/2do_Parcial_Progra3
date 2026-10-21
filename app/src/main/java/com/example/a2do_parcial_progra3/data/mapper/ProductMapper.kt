package com.example.a2do_parcial_progra3.data.mapper

import com.example.a2do_parcial_progra3.data.local.entity.ProductEntity
import com.example.a2do_parcial_progra3.data.remote.dto.ProductDto
import com.example.a2do_parcial_progra3.domain.model.Product

object ProductMapper {

    fun ProductDto.toEntity(): ProductEntity {
        return ProductEntity(
            id = id ?: 0,
            title = title.orEmpty(),
            description = description.orEmpty(),
            price = price ?: 0.0,
            category = category.orEmpty(),
            thumbnail = thumbnail.orEmpty()
        )
    }

    fun ProductEntity.toDomain(rateBOB: Double = 6.96): Product {
        return Product(
            id = id,
            title = title,
            description = description,
            price = price,
            priceBOB = price * rateBOB,
            category = category,
            thumbnail = thumbnail
        )
    }

    fun ProductDto.toDomain(rateBOB: Double = 6.96): Product {
        return Product(
            id = id ?: 0,
            title = title.orEmpty(),
            description = description.orEmpty(),
            price = price ?: 0.0,
            priceBOB = (price ?: 0.0) * rateBOB,
            category = category.orEmpty(),
            thumbnail = thumbnail.orEmpty()
        )
    }
}

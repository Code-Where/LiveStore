package com.codewhere.livestore.data.mapper

import com.codewhere.livestore.data.modal.ProductDto
import com.codewhere.livestore.data.modal.RatingDto
import com.codewhere.livestore.domain.modal.Product
import com.codewhere.livestore.domain.modal.Rating

fun ProductDto.toDomain(): Product{
    return Product(
        id = id,
        title = title,
        imageUrl = image,
        price = price,
        description = description,
        category = category,
        rating = rating.toDomain()
    )
}

fun RatingDto.toDomain(): Rating {
    return Rating(
        count = count,
        rate = rate
    )
}
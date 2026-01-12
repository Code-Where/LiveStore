package com.codewhere.livestore.data.modal

data class ProductDto(
    val category: String = "",
    val description: String = "",
    val id: Int = 0,
    val image: String = "",
    val price: Double = 0.0,
    val rating: RatingDto = RatingDto(0, 0.0),
    val title: String = ""
)

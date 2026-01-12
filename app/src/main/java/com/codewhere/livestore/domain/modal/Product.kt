package com.codewhere.livestore.domain.modal

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val price: Double,
    val rating: Rating,
    val title: String
) {
    companion object {
        fun empty() = Product("", "", -25, "", 0.0, Rating(0, 0.0), "")
    }

}

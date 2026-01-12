package com.codewhere.livestore.data.network

import com.codewhere.livestore.data.modal.ProductDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    @GET("products/category/{category}")
    fun getProductsByCategory(
        @Path("category") category: String
    ): Single<List<ProductDto>>

    @GET("products/{id}")
    fun getProductById(
        @Path("id") id: Int
    ): Single<ProductDto>
}
package com.codewhere.livestore.data.repository

import com.codewhere.livestore.data.mapper.toDomain
import com.codewhere.livestore.data.network.FakeStoreApi
import com.codewhere.livestore.domain.modal.Product
import com.codewhere.livestore.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single

class ProductsRepositoryImpl(
    private val api: FakeStoreApi
): ProductsRepository {
    override fun getElectronicsProducts(): Single<List<Product>> {
        return api.getProductsByCategory("electronics")
            .map { item ->
                item.map { it.toDomain() }
            }
    }
    override fun getClothingProducts(): Single<List<Product>> {
        return Single.zip(
            api.getProductsByCategory("men's clothing"),
            api.getProductsByCategory("women's clothing"),
        ){
            menClothing, womenClothing ->
            (menClothing + womenClothing).map { it.toDomain() }
        }
    }

    override fun getProductById(id: Int): Single<Product> {
        return api.getProductById(id)
            .map { it.toDomain() }
    }
}
package com.codewhere.livestore.domain.repository

import com.codewhere.livestore.domain.modal.Product
import io.reactivex.rxjava3.core.Single

interface ProductsRepository{
    fun getElectronicsProducts(): Single<List<Product>>
    fun getClothingProducts(): Single<List<Product>>
    fun getProductById(id: Int): Single<Product>
}
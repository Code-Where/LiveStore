package com.codewhere.livestore.domain.usecases

import com.codewhere.livestore.domain.modal.Product
import com.codewhere.livestore.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single

class GetProductUseCase(
    private val repository: ProductsRepository
) {
    operator fun invoke(id : Int): Single<Product> = repository.getProductById(id)
}
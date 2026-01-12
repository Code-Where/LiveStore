package com.codewhere.livestore.domain.usecases

import com.codewhere.livestore.domain.modal.Product
import com.codewhere.livestore.domain.repository.ProductsRepository
import io.reactivex.rxjava3.core.Single

class GetElectronicsProductUseCase(
    private val repository: ProductsRepository
) {
    operator fun invoke(): Single<List<Product>> = repository.getElectronicsProducts()
}
package com.codewhere.livestore.di

import com.codewhere.livestore.data.repository.ProductsRepositoryImpl
import com.codewhere.livestore.domain.repository.ProductsRepository
import io.reactivex.rxjava3.schedulers.Schedulers.single
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductsRepository> {
        ProductsRepositoryImpl(get())
    }
}
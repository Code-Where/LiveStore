package com.codewhere.livestore.di

import com.codewhere.livestore.domain.usecases.GetClothingProductUseCase
import com.codewhere.livestore.domain.usecases.GetElectronicsProductUseCase
import com.codewhere.livestore.domain.usecases.GetProductUseCase
import io.reactivex.rxjava3.schedulers.Schedulers.single
import org.koin.dsl.module

val useCaseModule = module {
    single { GetElectronicsProductUseCase(get()) }
    single { GetClothingProductUseCase(get()) }
    single { GetProductUseCase(get()) }
}
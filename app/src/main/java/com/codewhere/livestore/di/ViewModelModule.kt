package com.codewhere.livestore.di

import com.codewhere.livestore.presentation.detailed_product.viewmodel.ProductScreenViewModel
import com.codewhere.livestore.presentation.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ProductScreenViewModel(get(), get()) }
}

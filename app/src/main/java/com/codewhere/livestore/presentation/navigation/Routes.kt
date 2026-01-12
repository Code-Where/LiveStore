package com.codewhere.livestore.presentation.navigation

import com.codewhere.livestore.domain.modal.Product
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object SplashScreen : Routes()

    @Serializable
    object HomeScreen : Routes()

    @Serializable
    data class ProductScreen(val productId: Int) : Routes()

}
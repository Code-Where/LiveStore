package com.codewhere.livestore.presentation.home.state

import com.codewhere.livestore.common.constants.HomeTabs
import com.codewhere.livestore.domain.modal.Product

data class StateData (
    val selectedTab: HomeTabs = HomeTabs.ELECTRONICS,
    val electronicProducts: List<Product> = emptyList(),
    val clothingProducts: List<Product> = emptyList()
)
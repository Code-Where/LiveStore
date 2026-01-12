package com.codewhere.livestore.common.events

import com.codewhere.livestore.presentation.navigation.Routes

sealed class UiEvents {
    data class Navigate(val route: Routes) : UiEvents()
    data class ShowToast(val message: String) : UiEvents()
}
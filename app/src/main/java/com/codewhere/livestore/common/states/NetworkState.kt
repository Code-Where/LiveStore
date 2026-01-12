package com.codewhere.livestore.common.states

import com.codewhere.livestore.common.constants.ErrorTypes

sealed class NetworkState {
    object Loading : NetworkState()
    object Idle : NetworkState()
    data class Success(val data: Any) : NetworkState()
    data class Error(val errorType: ErrorTypes, val message: String) : NetworkState()
}
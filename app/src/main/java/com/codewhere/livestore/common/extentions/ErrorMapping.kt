package com.codewhere.livestore.common.extentions

import coil.network.HttpException
import com.codewhere.livestore.common.constants.ErrorTypes
import java.io.IOException

fun ErrorTypes.toUserString(): String{
    return when(this){
        ErrorTypes.NETWORK -> "No Internet Connection"
        ErrorTypes.SERVER -> "Internal Server Error"
        ErrorTypes.API -> "Something went wrong"
        ErrorTypes.UNKNOWN -> "We are facing some technical difficulties"
    }
}

fun Throwable.toErrorTypes(): ErrorTypes{
    return when(this){
        is IOException -> ErrorTypes.NETWORK
        is HttpException -> ErrorTypes.SERVER
        else -> ErrorTypes.UNKNOWN
    }
}
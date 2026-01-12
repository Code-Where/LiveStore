package com.codewhere.livestore.common.extentions

import java.util.Locale.getDefault

fun String.toSentenceCase(): String {
    if (isEmpty()) return this
    val loweredString = lowercase(getDefault())
    return loweredString.replaceFirstChar {
        it.uppercase(getDefault())
    }
}
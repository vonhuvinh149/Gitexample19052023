package com.android.product_api.common

import com.android.product_api.data.model.Product

sealed class AppResourceState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : AppResourceState<T>(data)
    class Error<T>(message: String) : AppResourceState<T>(message = message)
}
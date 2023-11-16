package com.android.food.utils

import com.android.food.data.api.dto.CartDTO
import com.android.food.data.api.model.Cart

class CartUtils {
    companion object {

        fun parseCartDTO(cartDTO: CartDTO?) :Cart{
            return Cart(
                id = cartDTO?.id ?: "",
                products = cartDTO?.products?.map { ProductUtils.parseProductDTO(it) } ?: emptyList(),
                idUser = cartDTO?.idUser ?: "",
                price = cartDTO?.price ?: 0,
                dateCreated = cartDTO?.dateCreated ?: ""
            )
        }

    }
}
package com.android.food.utils

import com.android.food.data.api.dto.ProductDTO
import com.android.food.data.api.model.Product

class ProductUtils {
    companion object {
        fun parseProductDTO(productDTO: ProductDTO): Product {
            return Product(
                id = productDTO.id ?: "",
                name = productDTO.name ?: "",
                address = productDTO.address ?: "",
                price = productDTO.price ?: 0,
                image = productDTO.img ?: "",
                quantity = productDTO.quantity ?: 0,
                gallery = productDTO.gallery ?: emptyList() ,
                dateUpdated = productDTO.dateCreated ?: ""
            )
        }
    }
}
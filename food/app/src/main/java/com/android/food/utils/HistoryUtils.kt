package com.android.food.utils

import com.android.food.data.api.dto.HistoryOrderDTO
import com.android.food.data.api.model.HistoryOrder

class HistoryUtils {
    companion object {
        fun parseHistoryDTO(historyOrderDTO: HistoryOrderDTO?): HistoryOrder {
            return HistoryOrder(
                id = historyOrderDTO?.id ?: "",
                products = historyOrderDTO?.products ?: emptyList(),
                idUser = historyOrderDTO?.idUser ?: "",
                price = historyOrderDTO?.price ?: 0,
                status = historyOrderDTO?.status ?: false,
                dateCreated = historyOrderDTO?.dateCreated ?: ""

            )
        }
    }
}
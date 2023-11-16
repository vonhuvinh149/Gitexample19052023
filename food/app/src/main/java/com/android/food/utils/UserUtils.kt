package com.android.food.utils

import com.android.food.data.api.dto.UserDTO
import com.android.food.data.api.model.User

class UserUtils {

    companion object {
        fun parseUserDTO(userDTO: UserDTO?): User {
            return User(
                email = userDTO?.email ?: "",
                name = userDTO?.name ?: "",
                phone = userDTO?.phone ?: "",
                token = userDTO?.token ?: "",
                registerDate = userDTO?.registerDate ?: ""
            )
        }
    }
}
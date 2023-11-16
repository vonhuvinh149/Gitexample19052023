package com.android.food.data.api.repository

import android.content.Context
import com.android.food.data.api.RetrofitClient
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.UserDTO
import retrofit2.Call

class AuthenticationRepository(
    var context: Context
) {

    private val apiService = RetrofitClient.getApiService(context)

    fun requestSignIn(email: String, password: String): Call<AppResponseDTO<UserDTO>> {
        val map = HashMap<String, Any>()
        map["email"] = email
        map["password"] = password
        return apiService.signIn(map)
    }

    fun requestSignUp(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ): Call<AppResponseDTO<UserDTO>> {
        val map = HashMap<String, Any>()
        map["email"] = email
        map["password"] = password
        map["name"] = name
        map["phone"] = phone
        map["address"] = address
        return apiService.signUp(map)
    }
}
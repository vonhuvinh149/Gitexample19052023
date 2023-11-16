package com.android.food.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.food.common.AppResource
import com.android.food.data.api.dto.AppResponseDTO
import com.android.food.data.api.dto.UserDTO
import com.android.food.data.api.model.User
import com.android.food.data.api.repository.AuthenticationRepository
import com.android.food.utils.UserUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(context: Context) : ViewModel() {
    private val authenticationRepository = AuthenticationRepository(context)
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val userLiveData = MutableLiveData<AppResource<User>>()

    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
    fun getUserLiveData(): LiveData<AppResource<User>> = userLiveData

    fun executeSignUp(
        email: String,
        name: String,
        password: String,
        phone: String,
        address: String
    ) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.requestSignUp(email, password, name, phone, address)
                .enqueue(object : Callback<AppResponseDTO<UserDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<UserDTO>>,
                        response: Response<AppResponseDTO<UserDTO>>
                    ) {
                        if (response.isSuccessful) {
                            val user = UserUtils.parseUserDTO(response.body()?.data)
                            userLiveData.value = AppResource.Success(user)
                        } else {
                            val errorResponse = response.errorBody()?.string() ?: "{}"
                            val jsonError = JSONObject(errorResponse)
                            userLiveData.value = AppResource.Error(jsonError.optString("message"))
                        }
                    }

                    override fun onFailure(call: Call<AppResponseDTO<UserDTO>>, t: Throwable) {
                        userLiveData.value = AppResource.Error(t.message.toString())
                        loadingLiveData.value = false
                    }
                })
        }
    }

}
package com.android.food.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.food.common.AppResource
import com.android.food.common.AppSharePreference
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

class SignInViewModel(val context: Context) : ViewModel() {
    private val authenticationRepository = AuthenticationRepository(context)

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val userLiveData = MutableLiveData<AppResource<User>>()
    private val tokenRefresh = MutableLiveData<AppResource<User>>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getUserLiveData(): LiveData<AppResource<User>> = userLiveData
    fun getTokenRefreshLiveData(): LiveData<AppResource<User>> = tokenRefresh

    // login account
    fun executeSignIn(email: String, password: String, context: Context) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository
                .requestSignIn(email, password)
                .enqueue(object : Callback<AppResponseDTO<UserDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<UserDTO>>,
                        response: Response<AppResponseDTO<UserDTO>>
                    ) {
                        if (response.isSuccessful) {
                            val user = UserUtils.parseUserDTO(response.body()?.data)
                            userLiveData.value = AppResource.Success(user)
                            // save token when login success
                            AppSharePreference(context).saveUser(
                                user
                            )
                        } else {
                            val errorResponse = response.errorBody()?.string() ?: "{}"
                            val jsonError = JSONObject(errorResponse)
                            userLiveData.value = AppResource.Error(jsonError.optString("message"))
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(call: Call<AppResponseDTO<UserDTO>>, t: Throwable) {
                        userLiveData.value = AppResource.Error(t.message.toString())
                        loadingLiveData.value = false
                    }
                })
        }
    }

    // update refresh
    fun executeTokenRefresh(password: String, email: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            authenticationRepository.requestRefreshToken(password, email)
                .enqueue(object : Callback<AppResponseDTO<UserDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<UserDTO>>,
                        response: Response<AppResponseDTO<UserDTO>>
                    ) {
                        if (response.isSuccessful) {
                            val user = UserUtils.parseUserDTO(response.body()?.data)
                            tokenRefresh.value = AppResource.Success(user)
                            // save token when login success
                            AppSharePreference(context).refreshToken(user.token)
                        } else {
                            val errorResponse = response.errorBody()?.string() ?: "{}"
                            val jsonError = JSONObject(errorResponse)
                            tokenRefresh.value = AppResource.Error(jsonError.optString("message"))
                        }
                        loadingLiveData.value = false
                    }

                    override fun onFailure(call: Call<AppResponseDTO<UserDTO>>, t: Throwable) {
                        tokenRefresh.value = AppResource.Error(t.message.toString())
                        loadingLiveData.value = false
                    }

                })
        }
    }

}
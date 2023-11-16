package com.android.food.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.food.common.AppResource
import com.android.food.common.AppSharePreference
import com.android.food.common.launchOnMain
import com.android.food.data.api.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(val context: Context) : ViewModel() {
    private var userLiveData = MutableLiveData<AppResource<User>>()
    private var loadingLivedata = MutableLiveData<Boolean>()

    fun getUserLiveData(): LiveData<AppResource<User>> = userLiveData
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLivedata

    fun getMyUser() {
        loadingLivedata.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user  =  AppSharePreference(context).getUser()
                launchOnMain { userLiveData.value = AppResource.Success(user) }
            } catch (e: Exception) {
                launchOnMain { userLiveData.value = AppResource.Error(e.message ?: "") }
            }finally {
                launchOnMain { loadingLivedata.value = false }
            }
        }
    }
}
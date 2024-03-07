package com.android.my_app_music.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.common.launchIO
import com.android.my_app_music.data.model.Advertisement
import com.android.my_app_music.data.repository.AdvertisingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AdvertisementViewModel : ViewModel() {

    private val adsRepository = AdvertisingRepository()

    private var listAdsLiveData = MutableLiveData<AppResource<MutableList<Advertisement>>>()

    fun getAdsLiveData(): LiveData<AppResource<MutableList<Advertisement>>> = listAdsLiveData

    fun executeData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                adsRepository.fetchAdvertisingData { lists ->
                    listAdsLiveData.value = AppResource.Success(lists)
                }
            } catch (e: Exception) {
                listAdsLiveData.value = AppResource.Error(e.message)
            }
        }
    }

}
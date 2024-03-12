package com.android.my_app_music.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SongViewModel : ViewModel() {

    private val songRepository = SongRepository()

    private var listSongsLiveData = MutableLiveData<AppResource<MutableList<Song>>>()
    private var listSongSearchLiveData = MutableLiveData<AppResource<MutableList<Song>>>()


    fun getSong(): LiveData<AppResource<MutableList<Song>>> = listSongsLiveData
    fun getSongSearch(): LiveData<AppResource<MutableList<Song>>> = listSongSearchLiveData

    fun executeSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                songRepository.fetchListSongDataFromFirebase { lists ->
                    listSongsLiveData.value = AppResource.Success(lists)
                }
            } catch (e: Exception) {
                listSongsLiveData.value = AppResource.Error(e.message)
            }
        }
    }

    fun executeSearchSong(searchValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                songRepository.fetchSearch(searchValue) { lists ->
                    listSongSearchLiveData.value = AppResource.Success(lists)
                }
            } catch (e: Exception) {
                listSongSearchLiveData.value = AppResource.Error(e.message)
            }
        }
    }
}
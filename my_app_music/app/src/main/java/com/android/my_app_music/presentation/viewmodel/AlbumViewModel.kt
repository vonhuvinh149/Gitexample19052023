package com.android.my_app_music.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Album
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.repository.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AlbumViewModel : ViewModel() {

    private val albumRepository = AlbumRepository()

    private var albumLiveData = MutableLiveData<AppResource<MutableList<Album>>>()
    private var listSongsLiveData = MutableLiveData<AppResource<MutableList<Song>>>()

    fun getAlbumsLivedata() : LiveData<AppResource<MutableList<Album>>> = albumLiveData
    fun getListSongAlbum(): LiveData<AppResource<MutableList<Song>>> = listSongsLiveData

    fun executeAlbums(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                albumRepository.getAlbumFromFirebase { data ->
                    albumLiveData.value = AppResource.Success(data)
                }
            }catch (e : Exception){
                albumLiveData.value = AppResource.Error(e.message)
            }
        }
    }

    fun executeGetListSongAlbum(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                albumRepository.getListSongAlbum(id) { data ->
                    listSongsLiveData.value = AppResource.Success(data)
                }
            } catch (e: Exception) {
                listSongsLiveData.value = AppResource.Error(e.message)
            }
        }
    }
}
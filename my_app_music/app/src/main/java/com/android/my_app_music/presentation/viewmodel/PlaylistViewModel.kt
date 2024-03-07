package com.android.my_app_music.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Playlist
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.repository.PlaylistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    private val playlistRepository = PlaylistRepository()

    private var playlistsLiveData = MutableLiveData<AppResource<MutableList<Playlist>>>()
    private var listSongsLiveData = MutableLiveData<AppResource<MutableList<Song>>>()

    fun getPlaylists(): LiveData<AppResource<MutableList<Playlist>>> = playlistsLiveData
    fun getListSongPlaylist(): LiveData<AppResource<MutableList<Song>>> = listSongsLiveData

    fun executePlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistRepository.getPlaylistFromFirebase { data ->
                    playlistsLiveData.value = AppResource.Success(data)
                }
            } catch (e: Exception) {
                playlistsLiveData.value = AppResource.Error(e.message)
            }
        }
    }

    fun executeGetListSongPlaylist(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistRepository.getListSongPlaylist(id) { data ->
                    listSongsLiveData.value = AppResource.Success(data)
                }
            } catch (e: Exception) {
                listSongsLiveData.value = AppResource.Error(e.message)
            }
        }
    }
}
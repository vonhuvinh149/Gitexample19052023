package com.android.my_app_music.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Playlist
import com.android.my_app_music.presentation.view.activity.PlaylistActivity
import com.android.my_app_music.presentation.view.adapter.PlaylistAdapter
import com.android.my_app_music.presentation.viewmodel.PlaylistViewModel
import com.android.my_app_music.utils.OnClickItem


class PlaylistFragment : Fragment() {

    private lateinit var playlistAdapter: PlaylistAdapter
    private lateinit var playlistViewModel: PlaylistViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var view: View
    private var lists: MutableList<Playlist> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_playlist, container, false)

        initView()
        observerData()
        event()

        return view
    }

    private fun event() {
        playlistAdapter.onClickItem(object : OnClickItem {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), PlaylistActivity::class.java)
                val playListId: Int = lists[position].id ?: 0
                val title: String = lists[position].title ?: ""
                val url: String = lists[position].backgroundImage ?: ""
                intent.putExtra(AppConstance.PLAYLIST_ID_KEY, playListId)
                intent.putExtra(AppConstance.PLAYLIST_TITLE_KEY, title)
                intent.putExtra(AppConstance.IMAGE_URL_PLAYLIST_KEY, url)
                startActivity(intent)
            }
        })
    }

    private fun observerData() {
        playlistViewModel =
            ViewModelProvider(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PlaylistViewModel() as T
                }
            })[PlaylistViewModel::class.java]

        playlistViewModel.getPlaylists().observe(requireActivity()) {
            when (it) {
                is AppResource.Success -> {
                    lists = it.data ?: mutableListOf()
                    playlistAdapter.updateData(lists)
                    recyclerView?.adapter = playlistAdapter
                }

                is AppResource.Error -> {
                    Log.e("BBB", it.message.toString())
                }
            }
        }

        playlistViewModel.executePlaylists()
    }

    private fun initView() {
        playlistAdapter = PlaylistAdapter()
        recyclerView = view.findViewById(R.id.recycler_view_playlist)
        recyclerView?.setHasFixedSize(false)
        playlistViewModel = PlaylistViewModel()
    }


}
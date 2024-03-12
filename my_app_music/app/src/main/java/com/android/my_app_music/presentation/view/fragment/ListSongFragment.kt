package com.android.my_app_music.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.service.SongService
import com.android.my_app_music.presentation.view.adapter.SongAdapter
import com.android.my_app_music.utils.OnClickItem

class ListSongFragment(
    private val position: Int,
    private val listSongs: MutableList<Song>
) : Fragment() {

    private var view: View? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var songAdapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_list_song, container, false)

        initView()
        event()

        return view
    }

    private fun event() {
        songAdapter.onClickItem(object : OnClickItem {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), SongService::class.java)
                intent.putExtra(AppConstance.POSITION_SONG_KEY, position)
                intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_CHANGE_SONG)
                requireContext().startService(intent)
            }
        })
    }

    private fun initView() {
        recyclerView = view?.findViewById(R.id.recycler_view_list_song)
        songAdapter = SongAdapter()
        songAdapter.updateData(listSongs)
        recyclerView?.adapter = songAdapter
        recyclerView?.setHasFixedSize(true)
    }

}
package com.android.my_app_music.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.presentation.view.activity.PlaySongActivity
import com.android.my_app_music.presentation.view.adapter.SongAdapter
import com.android.my_app_music.utils.OnClickItem

class PlayListSongFragment : Fragment() {

    private var view: View? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var songAdapter: SongAdapter
    val playSongActivity = PlaySongActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_play_list_song, container, false)

        initView()
        event()


        return view
    }

    private fun event() {
        songAdapter.onClickItem(object : OnClickItem{
            override fun onClick(position: Int) {
              playSongActivity.updatePosition(position)
            }
        })
    }

    private fun initView() {
        recyclerView = view?.findViewById(R.id.recycler_view_list_song)
        songAdapter = SongAdapter()
        songAdapter.updateData(PlaySongActivity.listSongs)
        recyclerView?.adapter = songAdapter
        recyclerView?.setHasFixedSize(true)
    }

}
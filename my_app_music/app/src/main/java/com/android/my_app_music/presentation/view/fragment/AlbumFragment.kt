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
import com.android.my_app_music.data.model.Album
import com.android.my_app_music.presentation.view.activity.AlbumActivity
import com.android.my_app_music.presentation.view.adapter.AlbumAdapter
import com.android.my_app_music.presentation.viewmodel.AlbumViewModel
import com.android.my_app_music.utils.OnClickItem


class AlbumFragment : Fragment() {

    private var view: View? = null
    private lateinit var albumAdapter: AlbumAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var albumViewModel: AlbumViewModel
    private var listAlbums: MutableList<Album> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_album, container, false)

        initView()
        observerData()
        event()

        return view
    }

    private fun event() {
        albumAdapter.onClickItem(object : OnClickItem {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), AlbumActivity::class.java)
                val albumId: Int = listAlbums[position].id ?: 0
                val title: String = listAlbums[position].albumName ?: ""
                val url: String = listAlbums[position].albumImage ?: ""
                intent.putExtra(AppConstance.ALBUM_ID_KEY, albumId)
                intent.putExtra(AppConstance.ALBUM_TITLE_KEY, title)
                intent.putExtra(AppConstance.IMAGE_URL_ALBUM_KEY, url)
                startActivity(intent)
            }
        })
    }

    private fun observerData() {
        albumViewModel.getAlbumsLivedata().observe(viewLifecycleOwner) {
            when (it) {
                is AppResource.Success -> {
                    listAlbums = it.data ?: mutableListOf()
                    albumAdapter.updateData(listAlbums)
                    recyclerView?.adapter = albumAdapter
                    recyclerView?.setHasFixedSize(true)
                }

                is AppResource.Error -> {
                    Log.e("BBB", it.message.toString())
                }
            }
        }
        albumViewModel.executeAlbums()
    }

    private fun initView() {
        albumAdapter = AlbumAdapter()
        recyclerView = view?.findViewById(R.id.recycler_view_album)

        albumViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AlbumViewModel() as T
            }
        })[AlbumViewModel::class.java]
    }

}
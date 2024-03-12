package com.android.my_app_music.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.presentation.view.adapter.SongAdapter
import com.android.my_app_music.presentation.viewmodel.PlaylistViewModel
import com.android.my_app_music.utils.OnClickItem
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PlaylistActivity : AppCompatActivity() {

    private lateinit var playlistViewModel: PlaylistViewModel
    private var recyclerView: RecyclerView? = null
    private lateinit var songAdapter: SongAdapter
    private var btnPlayAll: CircleImageView? = null
    private var imgBackground: ImageView? = null
    private var tvTitle: TextView? = null
    private var listSongs: MutableList<Song> = mutableListOf()
    private var title: String? = ""
    private var imgBackgroundUrl: String? = ""
    private var playlistId: Int = 0
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        initView()
        createToolBar()
        getDataFromIntent()
        observerData()
        event()

    }

    private fun event() {
        songAdapter.onClickItem(object : OnClickItem {
            override fun onClick(position: Int) {
                val intent = Intent(this@PlaylistActivity, PlaySongActivity::class.java)
                intent.putExtra(AppConstance.POSITION_SONG_KEY, position)
                intent.putExtra(AppConstance.LIST_SONG_KEY, ArrayList(listSongs))
                startActivity(intent)
                finish()
            }
        })

        btnPlayAll?.setOnClickListener {
            val intent = Intent(this@PlaylistActivity, PlaySongActivity::class.java)
            intent.putExtra(AppConstance.POSITION_SONG_KEY, 0)
            intent.putExtra(AppConstance.LIST_SONG_KEY, ArrayList(listSongs))
            startActivity(intent)
            finish()
        }

        toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observerData() {

        playlistViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlaylistViewModel() as T
            }
        })[PlaylistViewModel::class.java]

        playlistViewModel.getListSongPlaylist().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    listSongs = it.data ?: mutableListOf()
                    songAdapter.updateData(listSongs)
                    recyclerView?.adapter = songAdapter
                }

                is AppResource.Error -> {
                    Log.e("BBB", it.message.toString())
                }

                else -> {

                }
            }
        }

        playlistViewModel.executeGetListSongPlaylist(playlistId)
    }

    private fun initView() {
        btnPlayAll = findViewById(R.id.btn_play_all_playlist)
        imgBackground = findViewById(R.id.img_background_playlist)
        tvTitle = findViewById(R.id.tv_title_playlist_song)
        recyclerView = findViewById(R.id.recycler_view_song_playlist)
        toolbar = findViewById(R.id.toolbar_playlist)
        recyclerView?.setHasFixedSize(true)
        songAdapter = SongAdapter()
    }

    private fun getDataFromIntent() {
        if (intent != null) {
            title = intent.getStringExtra(AppConstance.PLAYLIST_TITLE_KEY)
            playlistId = intent.getIntExtra(AppConstance.PLAYLIST_ID_KEY, 0)
            imgBackgroundUrl = intent.getStringExtra(AppConstance.IMAGE_URL_PLAYLIST_KEY)
            tvTitle?.text = title
            Picasso.get().load(imgBackgroundUrl).into(imgBackground)
        }
    }

    private fun createToolBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }
}
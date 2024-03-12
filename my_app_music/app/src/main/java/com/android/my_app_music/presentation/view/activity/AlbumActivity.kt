package com.android.my_app_music.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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
import com.android.my_app_music.presentation.viewmodel.AlbumViewModel
import com.android.my_app_music.utils.OnClickItem
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class AlbumActivity : AppCompatActivity() {

    private var appBarLayout: AppBarLayout? = null
    private var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    private var toolbar: Toolbar? = null
    private var btnPlayAll: CircleImageView? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var songAdapter: SongAdapter
    private var imgAlbum: ImageView? = null
    private var listSongs: MutableList<Song> = mutableListOf()
    private var title: String? = ""
    private var imgBackgroundUrl: String? = ""
    private var albumId: Int = 0
    private var albumViewModel: AlbumViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        initView()
        createToolBar()
        getDataFromIntent()
        observerData()
        event()
    }

    private fun event() {
        songAdapter.onClickItem(object : OnClickItem {
            override fun onClick(position: Int) {
                val intent = Intent(this@AlbumActivity, PlaySongActivity::class.java)
                intent.putExtra(AppConstance.POSITION_SONG_KEY, position)
                intent.putExtra(AppConstance.LIST_SONG_KEY, ArrayList(listSongs))
                startActivity(intent)
                finish()
            }
        })

        btnPlayAll?.setOnClickListener {
            val intent = Intent(this@AlbumActivity, PlaySongActivity::class.java)
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
        albumViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AlbumViewModel() as T
            }
        })[AlbumViewModel::class.java]

        albumViewModel?.getListSongAlbum()?.observe(this) {
            when (it) {
                is AppResource.Success -> {
                    listSongs = it.data ?: mutableListOf()
                    songAdapter.updateData(listSongs)
                    recyclerView?.adapter = songAdapter
                    recyclerView?.setHasFixedSize(true)
                }

                is AppResource.Error -> {
                    Log.e("BBB", it.message.toString())
                }
            }
        }
        albumViewModel?.executeGetListSongAlbum(albumId)
    }

    private fun initView() {
        imgAlbum = findViewById(R.id.img_background_album)
        appBarLayout = findViewById(R.id.appBarLayout)
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout)
        toolbar = findViewById(R.id.toolbar_album)
        btnPlayAll = findViewById(R.id.btn_play_all_albums)
        recyclerView = findViewById(R.id.recycler_view_album_song)
        recyclerView?.setHasFixedSize(true)
        songAdapter = SongAdapter()

    }

    private fun createToolBar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun getDataFromIntent() {
        if (intent != null) {
            title = intent.getStringExtra(AppConstance.ALBUM_TITLE_KEY)
            albumId = intent.getIntExtra(AppConstance.ALBUM_ID_KEY, 0)
            imgBackgroundUrl = intent.getStringExtra(AppConstance.IMAGE_URL_ALBUM_KEY)
            Picasso.get().load(imgBackgroundUrl).into(imgAlbum)
        }
    }
}
package com.android.my_app_music.presentation.view.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.data.SongSharedPreference
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.service.SongService
import com.android.my_app_music.presentation.view.adapter.ViewPagerPlaySongAdapter
import com.android.my_app_music.presentation.view.fragment.ListSongFragment
import com.android.my_app_music.presentation.view.fragment.MusicDiscFragment
import com.android.my_app_music.utils.DataChangeFromServiceListener
import java.util.concurrent.TimeUnit

class PlaySongActivity : AppCompatActivity() {

    private var toolBarPlaySong: Toolbar? = null
    private var seekBar: SeekBar? = null
    private lateinit var viewPager: ViewPager
    private var tvTime: TextView? = null
    private var tvTimeTotal: TextView? = null
    private var btnPrev: ImageButton? = null
    private var btnNext: ImageButton? = null
    private var btnShuffle: ImageButton? = null
    private var btnRepeat: ImageButton? = null
    private var btnPlay: ImageButton? = null
    private val handler = Handler()
    private var isCheckRepeat: Boolean = false
    private var isCheckShuffle: Boolean = false
    private var isPlaying: Boolean = false
    private lateinit var musicDiscFragment: MusicDiscFragment

    private lateinit var viewPagerPlaySongAdapter: ViewPagerPlaySongAdapter
    private lateinit var songService: SongService
    private var isBoundService: Boolean = false
    private var duration = 0
    private var currentPosition = 0

    private var position: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private var listSongs: ArrayList<Song> = arrayListOf()
    private lateinit var songSharedPreference: SongSharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play_song)

        initView()
        getDataFromIntent()
        musicDiscFragment = MusicDiscFragment(position, listSongs)
        createViewPager()

        val intent = Intent(this@PlaySongActivity, SongService::class.java)
        intent.putExtra(AppConstance.POSITION_SONG_KEY, position)
        intent.putExtra(AppConstance.LIST_SONG_KEY, listSongs)
        intent.putExtra(AppConstance.CHECK_IS_REPEAT, isCheckRepeat)
        intent.putExtra(AppConstance.CHECK_IS_SHUFFLE, isCheckShuffle)
        startService(intent)

        val filter = IntentFilter("YOUR_ACTION_NAME")
        registerReceiver(broadcastReceiver, filter)

        val currentPosition = IntentFilter("CURRENT_POSITION")
        registerReceiver(currentPositionReceiver, currentPosition)

        event()
    }

    private fun createViewPager() {
        viewPagerPlaySongAdapter = ViewPagerPlaySongAdapter(supportFragmentManager)
        viewPagerPlaySongAdapter.addFragment(ListSongFragment(position, listSongs))
        viewPagerPlaySongAdapter.addFragment(musicDiscFragment)

        viewPager.adapter = viewPagerPlaySongAdapter
        viewPager.currentItem = 1
    }

    private fun getDataFromIntent() {
        if (intent != null) {
            position = intent.getIntExtra(AppConstance.POSITION_SONG_KEY, 0)
            if (intent.hasExtra(AppConstance.LIST_SONG_KEY)) {
                listSongs =
                    intent.getParcelableArrayListExtra(AppConstance.LIST_SONG_KEY) ?: arrayListOf()
            }
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action == "YOUR_ACTION_NAME") {
                    val position = intent.getIntExtra(AppConstance.CHANGE_POSITION_FROM_SERVICE, 0)
                    duration = intent.getIntExtra(AppConstance.DURATION_POSITION, 0)
                    isPlaying = intent.getBooleanExtra(AppConstance.CHECK_IS_PLAY, false)
                    updateView()
                    setupSeekbar(duration)
                    updateTimeDuration(duration)
                    musicDiscFragment.updateView(position)
                }
            }
        }
    }

    private val currentPositionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                currentPosition = intent.getIntExtra(AppConstance.CURRENT_POSITION, 0)
                updateCurrentPosition(currentPosition)
            }
        }

    }

    private fun updateCurrentPosition(currentPosition: Int) {
        val formatCurrentPosition = formatTimeMediaPlayer(currentPosition)
        tvTime?.text = formatCurrentPosition
        seekBar?.progress = currentPosition
    }

    fun updateView() {
        if (isPlaying) {
            btnPlay?.setImageResource(R.drawable.ic_pause)
        } else {
            btnPlay?.setImageResource(R.drawable.ic_play)
        }
    }


    private fun event() {

        btnNext?.setOnClickListener {
            nextSong()
        }

        btnPrev?.setOnClickListener {
            prevSong()
        }

        btnShuffle?.setOnClickListener {
            shuffleSong()
        }

        btnRepeat?.setOnClickListener {
            repeatSong()
        }

        btnPlay?.setOnClickListener {
            playSong()
        }
    }

    private fun shuffleSong() {
        isCheckShuffle = songSharedPreference.getIsShuffle()
        if (isCheckShuffle) {
            btnShuffle?.setImageResource(R.drawable.ic_suf)
        } else {
            btnShuffle?.setImageResource(R.drawable.ic_shuffle_blue)
        }
        songSharedPreference.saveIsCheckShuffle(!isCheckShuffle)
        val intent = Intent(this@PlaySongActivity, SongService::class.java)
        intent.putExtra(AppConstance.CHECK_IS_SHUFFLE, !isCheckShuffle)
        startService(intent)
    }

    private fun playSong() {
        val intent = Intent(this@PlaySongActivity, SongService::class.java)
        if (isPlaying) {
            intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_PAUSE)
            btnPlay?.setImageResource(R.drawable.ic_play)
        } else {
            intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_PLAY)
            btnPlay?.setImageResource(R.drawable.ic_pause)
        }
        startService(intent)
    }

    private fun nextSong() {
        val intent = Intent(this@PlaySongActivity, SongService::class.java)
        intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_NEXT)
        startService(intent)
    }

    private fun prevSong() {
        val intent = Intent(this@PlaySongActivity, SongService::class.java)
        intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_PREV)
        startService(intent)
    }

    private fun repeatSong() {
        isCheckRepeat = songSharedPreference.getIsRepeat()
        if (isCheckRepeat) {
            btnRepeat?.setImageResource(R.drawable.ic_repeat)
        } else {
            btnRepeat?.setImageResource(R.drawable.ic_repeat_blue)
        }
        songSharedPreference.saveIsCheckRepeat(!isCheckRepeat)
        val intent = Intent(this@PlaySongActivity, SongService::class.java)

        intent.putExtra(AppConstance.CHECK_IS_REPEAT, !isCheckRepeat)
        startService(intent)
    }

    private fun updateTimeDuration(duration: Int) {
        val timeSong = formatTimeMediaPlayer(duration)
        tvTimeTotal?.text = timeSong
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun formatTimeMediaPlayer(durationInMillis: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis.toLong()) -
                TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun initView() {
        toolBarPlaySong = findViewById(R.id.toolbar_play_song)
        seekBar = findViewById(R.id.seekbar_play)
        viewPager = findViewById(R.id.view_pager_play_song)
        tvTime = findViewById(R.id.tv_time_play)
        tvTimeTotal = findViewById(R.id.tv_time_total)
        btnPrev = findViewById(R.id.btn_prev)
        btnNext = findViewById(R.id.btn_next)
        btnShuffle = findViewById(R.id.btn_suf)
        btnRepeat = findViewById(R.id.btn_repeat)
        btnPlay = findViewById(R.id.btn_play)
        mediaPlayer = MediaPlayer()
        songService = SongService()
        songSharedPreference = SongSharedPreference(this)

        isCheckRepeat = songSharedPreference.getIsRepeat()
        isCheckShuffle = songSharedPreference.getIsShuffle()

        if (isCheckShuffle) {
            btnShuffle?.setImageResource(R.drawable.ic_shuffle_blue)
        } else {
            btnShuffle?.setImageResource(R.drawable.ic_suf)
        }
        if (isCheckRepeat) {
            btnRepeat?.setImageResource(R.drawable.ic_repeat_blue)
        } else {
            btnRepeat?.setImageResource(R.drawable.ic_repeat)
        }

        setSupportActionBar(toolBarPlaySong)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolBarPlaySong?.setNavigationOnClickListener {
            finish()
        }
        toolBarPlaySong?.setTitleTextColor(Color.WHITE)
    }

    private fun setupSeekbar(duration: Int) {
        seekBar?.max = duration
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val intent = Intent(this@PlaySongActivity, SongService::class.java)
                intent.putExtra(AppConstance.UPDATE_SEEK_TO, seekBar?.progress)
                startService(intent)
            }
        })
    }
}
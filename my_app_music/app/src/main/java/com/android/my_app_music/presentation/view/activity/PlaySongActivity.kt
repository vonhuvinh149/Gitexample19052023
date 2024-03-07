package com.android.my_app_music.presentation.view.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.data.service.SongService
import com.android.my_app_music.presentation.view.adapter.ViewPagerPlaySongAdapter
import com.android.my_app_music.presentation.view.fragment.MusicDiscFragment
import com.android.my_app_music.presentation.view.fragment.PlayListSongFragment
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

    private lateinit var viewPagerPlaySongAdapter: ViewPagerPlaySongAdapter
    private lateinit var songService: SongService
    private var isBoundService: Boolean = false

    companion object {
        var position: Int = 0
        private var mediaPlayer: MediaPlayer? = null
        var listSongs: ArrayList<Song> = arrayListOf()
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as SongService.LocalBinder
            songService = binder.getService()
            isBoundService = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBoundService = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_play_song)

        initView()
        getDataFromIntent()

        val intent = Intent(this@PlaySongActivity, SongService::class.java)
        intent.putExtra(AppConstance.POSITION_SONG_KEY, position)
        intent.putExtra(AppConstance.LIST_SONG_KEY, listSongs)
        startService(intent)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        event()
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

    fun updatePosition(index: Int) {
        if (index == position) {
            return
        } else {
            val intent = Intent(this@PlaySongActivity, SongService::class.java)
            intent.putExtra(AppConstance.CHECK_CLICK_ITEM, true)
            intent.putExtra(AppConstance.POSITION_SONG_KEY, index)
            startService(intent)
        }
    }


    private fun createMediaPlayer() {
        mediaPlayer?.reset()
        try {
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
            mediaPlayer?.setDataSource(listSongs[position].songUrl)
            mediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            Log.e("BBB", e.message.toString())
        }
    }

    private fun event() {
        createMediaPlayer()
        mediaPlayer?.setOnCompletionListener {
            if (isCheckRepeat) {
                mediaPlayer?.seekTo(0)
                mediaPlayer?.start()
            } else {
                if (isCheckShuffle) {
                    if (mediaPlayer?.isPlaying == true) {
                        mediaPlayer?.stop()
                        btnPlay?.setImageResource(R.drawable.ic_play)
                    }
                    val random = (0 until listSongs.size).random()
                    position = random
                    createMediaPlayer()
                    btnPlay?.setImageResource(R.drawable.ic_pause)
                } else {
                    nextSong()
                }
            }
        }

        val durationInMillis = mediaPlayer?.duration ?: 0
        val durationText = formatDuration(durationInMillis)

        btnPlay?.setImageResource(R.drawable.ic_pause)
        tvTimeTotal?.text = durationText
        updateTimeSong()
        setupSeekbar()

        btnNext?.setOnClickListener {
            val intent = Intent(this@PlaySongActivity, SongService::class.java)
            intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_NEXT)
            startService(intent)
        }

        btnPrev?.setOnClickListener {
            val intent = Intent(this@PlaySongActivity, SongService::class.java)
            intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_PREV)
            startService(intent)
        }

        btnShuffle?.setOnClickListener {
            shuffleSong()
        }

        btnRepeat?.setOnClickListener {
            isCheckRepeat = songService.getIsRepeat()
            if (isCheckRepeat) {
                btnRepeat?.setImageResource(R.drawable.ic_repeat_blue)
            } else {
                btnRepeat?.setImageResource(R.drawable.ic_repeat)
            }
            val intent = Intent(this@PlaySongActivity, SongService::class.java)
            intent.putExtra(AppConstance.ACTION_RECEIVER_KEY, SongService.ACTION_REPEAT)
            startService(intent)
        }

        btnPlay?.setOnClickListener {
            isPlaying = songService.getIsPlaying()
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
    }

    private fun shuffleSong() {
        isCheckShuffle = !isCheckShuffle
        if (isCheckShuffle) {
            btnShuffle?.setImageResource(R.drawable.ic_shuffle_blue)
        } else {
            btnShuffle?.setImageResource(R.drawable.ic_suf)
        }
    }

    private fun prevSong() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            btnPlay?.setImageResource(R.drawable.ic_play)
        }
        position--
        if (position < 0) {
            position = listSongs.size - 1
        }
        createMediaPlayer()
        btnPlay?.setImageResource(R.drawable.ic_pause)
    }


    private fun nextSong() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            btnPlay?.setImageResource(R.drawable.ic_play)
        }
        position++
        if (position > listSongs.size - 1) {
            position = 0
        }
        MusicDiscFragment.updatePosition(position)
        createMediaPlayer()
        btnPlay?.setImageResource(R.drawable.ic_pause)
    }

    private fun repeatSong() {
        isCheckRepeat = !isCheckRepeat
        if (isCheckRepeat) {
            btnRepeat?.setImageResource(R.drawable.ic_repeat_blue)
        } else {
            btnRepeat?.setImageResource(R.drawable.ic_repeat)
        }
    }

    private fun playSong() {
        if (mediaPlayer?.isPlaying == true) {
            btnPlay?.setImageResource(R.drawable.ic_play)
            mediaPlayer?.pause()
            updateTimeSong()
        } else {
            btnPlay?.setImageResource(R.drawable.ic_pause)
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition ?: 0)
            mediaPlayer?.start()
        }
    }

    private fun updateTimeSong() {
        handler.postDelayed(Runnable {
            val timeSong = formatDuration(mediaPlayer?.currentPosition ?: 0)
            tvTime?.text = timeSong
            seekBar?.progress = mediaPlayer?.currentPosition ?: 0
            updateTimeSong()
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Giải phóng tài nguyên khi hoạt động bị hủy
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun formatDuration(durationInMillis: Int): String {
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
        viewPagerPlaySongAdapter = ViewPagerPlaySongAdapter(supportFragmentManager)
        viewPagerPlaySongAdapter.addFragment(PlayListSongFragment())
        viewPagerPlaySongAdapter.addFragment(MusicDiscFragment())
        viewPager.adapter = viewPagerPlaySongAdapter

        setSupportActionBar(toolBarPlaySong)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolBarPlaySong?.setNavigationOnClickListener {
            finish()
        }
        toolBarPlaySong?.setTitleTextColor(Color.WHITE)
    }

    private fun setupSeekbar() {
        seekBar?.max = mediaPlayer?.duration ?: 0
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer?.seekTo(seekBar?.progress ?: 0)
            }
        })
    }
}
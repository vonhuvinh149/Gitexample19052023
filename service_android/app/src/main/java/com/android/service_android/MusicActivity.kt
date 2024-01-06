package com.android.service_android

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class MusicActivity : AppCompatActivity() {

    private var btnPrev: ImageButton? = null
    private var btnNext: ImageButton? = null
    private var btnPlay: ImageButton? = null
    private var tvTimeSong: TextView? = null
    private var tvTimeTotal: TextView? = null
    private var tvNameSong: TextView? = null
    private var seekBar: SeekBar? = null
    private var listSong: MutableList<Song> = mutableListOf()
    private var index: Int = 0;
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songAdapter: SongAdapter
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        initView()
        addSong()
        createMediaPlayer()

        songAdapter = SongAdapter()
        songAdapter.updateListSong(listSong)
        recyclerView?.adapter = songAdapter

        event()

    }

    private fun formatTime(time: Int): String {
        val formatTime = SimpleDateFormat("mm:ss")
        return formatTime.format(time)
    }


    private fun event() {
        btnPlay?.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                btnPlay?.setImageResource(R.drawable.play)
            } else {
                mediaPlayer.start()
                btnPlay?.setImageResource(R.drawable.pause)
            }
        }

        btnPrev?.setOnClickListener {
            if (index > 0) {
                index--
                mediaPlayer.stop()
                btnPlay?.setImageResource(R.drawable.play)
                createMediaPlayer()
            } else {
                index = listSong.size - 1
                mediaPlayer.stop()
                btnPlay?.setImageResource(R.drawable.play)
                createMediaPlayer()
            }
        }

        btnNext?.setOnClickListener {
            if (index < listSong.size - 1) {
                index++
                mediaPlayer.stop()
                btnPlay?.setImageResource(R.drawable.play)
                createMediaPlayer()
            } else {
                index = 0
                mediaPlayer.stop()
                btnPlay?.setImageResource(R.drawable.play)
                createMediaPlayer()

            }
        }

        songAdapter.setOnClickItemSong(object : SongAdapter.OnClickItemSong {
            override fun onClick(position: Int) {
                index = position
                songAdapter = SongAdapter()
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                }
                btnPlay?.setImageResource(R.drawable.play)
                createMediaPlayer()
            }

        })

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(seekBar?.progress ?: 0)
            }
        })
    }

    private fun createMediaPlayer() {

        mediaPlayer = MediaPlayer.create(this@MusicActivity, listSong[index].file)
        tvNameSong?.text = listSong[index].title
        tvTimeTotal?.text = formatTime(mediaPlayer.duration)
        seekBar?.max = mediaPlayer.duration
        updateTimeSong()
    }

    private fun updateTimeSong() {
        val handler = Handler(Looper.getMainLooper())
        val delayMillis: Long = 500

        val repeatTask = object : Runnable {
            override fun run() {
                tvTimeSong?.text = formatTime(mediaPlayer.currentPosition)
                seekBar?.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, delayMillis)
            }
        }

        handler.postDelayed(repeatTask, delayMillis)
    }

    private fun addSong() {
        listSong.add(
            Song(
                "Hit me up",
                "Binz",
                "https://avatar-ex-swe.nixcdn.com/song/2023/10/16/7/6/e/4/1697472547132_640.jpg",
                R.raw.hit_me_up
            )
        )
        listSong.add(
            Song(
                "Từng quen",
                "Binz",
                "https://photo-resize-zmp3.zmdcdn.me/w600_r1x1_jpeg/cover/9/7/4/c/974c4f42b6143c56af330323d86a0b7f.jpg",
                R.raw.tung_quen
            )
        )
        listSong.add(
            Song(
                "BSNL1",
                "Binz",
                "https://i.scdn.co/image/ab67616d0000b2734efa50dd4022eda4bbb4c68a",
                R.raw.bsnl1
            )
        )
        listSong.add(
            Song(
                "BSNL2",
                "Binz",
                "https://i1.sndcdn.com/artworks-000207540269-vqchft-t500x500.jpg",
                R.raw.bsnl_2
            )
        )
        listSong.add(
            Song(
                "Cao ốc 20",
                "Binz",
                "https://i1.sndcdn.com/artworks-000556537428-fuinlx-t500x500.jpg",
                R.raw.cao_oc_202
            )
        )
        listSong.add(
            Song(
                "Tuý âm",
                "Binz",
                "data:image/jpeg;base64,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",
                R.raw.tuy_am
            )
        )
        listSong.add(
            Song(
                "Cưới thôi",
                "Binz",
                "https://i.scdn.co/image/ab67616d0000b2737cd903c07645cda72a2d59cd",
                R.raw.cuoi_thoi
            )
        )
    }

    private fun initView() {
        btnPrev = findViewById(R.id.btn_prev)
        btnNext = findViewById(R.id.btn_next)
        btnPlay = findViewById(R.id.btn_play)
        tvTimeSong = findViewById(R.id.tv_time_song)
        tvTimeTotal = findViewById(R.id.tv_time_total)
        tvNameSong = findViewById(R.id.tv_name_song)
        seekBar = findViewById(R.id.seekbar)
        recyclerView = findViewById(R.id.recycler_view)

    }
}
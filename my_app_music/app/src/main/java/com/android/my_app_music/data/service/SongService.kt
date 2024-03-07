package com.android.my_app_music.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.android.my_app_music.R
import com.android.my_app_music.common.AppConstance
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.presentation.view.activity.PlaySongActivity

class SongService : Service() {

    private val CHANEL_ID = "my-chanel"

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var notificationManager: NotificationManager
    private var position: Int = 0
    private var listSongs: ArrayList<Song> = arrayListOf()
    private var isPlaying: Boolean = false
    private var isDataFromActivity = false
    private var song: Song = Song()
    private val binder = LocalBinder()
    private var isRepeatSong = false
    private var isClickItem = false

    companion object {
        const val ACTION_PLAY = 1
        const val ACTION_PAUSE = 2
        const val ACTION_PREV = 3
        const val ACTION_NEXT = 4
        const val ACTION_CLEAR = 5
        const val ACTION_REPEAT = 6
        const val ACTION_SHUFFLE = 7
    }

    inner class LocalBinder : Binder() {
        fun getService(): SongService {
            return this@SongService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("BBB", "onBind")
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        Log.e("BBB", "onCreate")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("BBB", "unBound")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null) {
            isClickItem = intent.getBooleanExtra(AppConstance.CHECK_CLICK_ITEM, false)
            if (!isDataFromActivity) {
                position = intent.getIntExtra(AppConstance.POSITION_SONG_KEY, 0)
                listSongs =
                    intent.getParcelableArrayListExtra(AppConstance.LIST_SONG_KEY) ?: arrayListOf()
                isDataFromActivity = true
                isPlaying = true
                mediaPlayer?.setOnCompletionListener {
                    if (isRepeatSong) {
                        mediaPlayer?.isLooping = true
                    } else {
                        nextSong()
                    }
                }
            }
//            if (isClickItem) {
////                position = intent.getIntExtra(AppConstance.POSITION_SONG_KEY, 0)
////                isClickItem = !isClickItem
////            }
            val actionMusic = intent.getIntExtra(AppConstance.ACTION_RECEIVER_KEY, 0)
            if (listSongs.size > 0) {
                song = listSongs[position]
            }
            setupMediaPlayer(song.songUrl ?: "")
            handleActionNotification(actionMusic)
            createNotification()
            playSong()
        }

        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun setupMediaPlayer(url: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        try {
            mediaPlayer?.setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            Log.e("BBB", e.message.toString())
        }
    }


    private fun setupRemoteView(): RemoteViews {
        val remoteView = RemoteViews(packageName, R.layout.layout_custom_notification)
        remoteView.setTextViewText(R.id.tv_title_nof, listSongs[position].songName)
        remoteView.setTextViewText(R.id.tv_singer_name_nof, listSongs[position].singerName)
        remoteView.setImageViewResource(R.id.btn_prev_nof, R.drawable.ic_prev)
        remoteView.setImageViewResource(R.id.btn_play_nof, R.drawable.ic_pause)
        remoteView.setImageViewResource(R.id.btn_next_nof, R.drawable.ic_next)
        remoteView.setImageViewResource(R.id.btn_clear_nof, R.drawable.ic_close)
        remoteView.setOnClickPendingIntent(
            R.id.btn_clear_nof,
            getPendingIntent(this, ACTION_CLEAR)
        )
        remoteView.setOnClickPendingIntent(
            R.id.btn_prev_nof,
            getPendingIntent(this, ACTION_PREV)
        )
        remoteView.setOnClickPendingIntent(
            R.id.btn_next_nof,
            getPendingIntent(this, ACTION_NEXT)
        )

        if (isPlaying) {
            remoteView.setOnClickPendingIntent(
                R.id.btn_play_nof,
                getPendingIntent(this, ACTION_PAUSE)
            )
            remoteView.setImageViewResource(R.id.btn_play_nof, R.drawable.ic_pause)
        } else {
            remoteView.setOnClickPendingIntent(
                R.id.btn_play_nof,
                getPendingIntent(this, ACTION_PLAY)
            )
            remoteView.setImageViewResource(R.id.btn_play_nof, R.drawable.ic_play)
        }

        return remoteView
    }

    private fun createNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this@SongService, PlaySongActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setCustomContentView(setupRemoteView())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent).setSound(null)
            .setCustomBigContentView(setupRemoteView())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANEL_ID,
                "Version New",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        startForeground(1, notification.build())
    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(AppConstance.ACTION_SERVICE_KEY, action)
        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun handleActionNotification(action: Int) {
        when (action) {
            ACTION_NEXT -> {
                nextSong()
            }

            ACTION_PLAY -> {
                resumeMusic()
            }

            ACTION_PAUSE -> {
                pauseSong()
            }

            ACTION_PREV -> {
                prevSong()
            }

            ACTION_CLEAR -> {
                stopSelf()
            }

            ACTION_REPEAT -> {
                repeatSong()
            }

            ACTION_SHUFFLE -> {
                shuffleSong()
            }
        }
    }

    private fun shuffleSong() {
        TODO("Not yet implemented")
    }

    private fun repeatSong() {

        if (mediaPlayer != null) {
            isRepeatSong = !isRepeatSong
        }
    }

    private fun prevSong() {
        if (mediaPlayer != null) {
            mediaPlayer?.reset()
            mediaPlayer?.stop()
        }
        position--
        if (position < 0) {
            position = listSongs.size - 1
        }
        setupMediaPlayer(listSongs[position].songUrl ?: "")
        resumeMusic()
    }

    private fun nextSong() {
        if (mediaPlayer != null) {
            mediaPlayer?.reset()
            mediaPlayer?.stop()
        }
        position++
        if (position > listSongs.size - 1) {
            position = 0
        }
        setupMediaPlayer(listSongs[position].songUrl ?: "")
        playSong()
    }

    private fun pauseSong() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
        }
    }

    private fun resumeMusic() {
        if (mediaPlayer != null) {
            mediaPlayer?.start()
            isPlaying = true
        }
    }

    fun getIsPlaying(): Boolean {
        return isPlaying
    }

    fun getIsRepeat(): Boolean {
        return !isRepeatSong
    }

    private fun playSong() {
        if (mediaPlayer != null) {
            mediaPlayer?.setOnPreparedListener {
                mediaPlayer?.start()
                isPlaying = true
            }
        }
    }
}
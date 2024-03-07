package com.android.my_app_music.presentation.view.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.my_app_music.R
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.presentation.view.activity.PlaySongActivity
import com.android.my_app_music.presentation.view.activity.PlaySongActivity.Companion.listSongs
import com.android.my_app_music.presentation.view.activity.PlaySongActivity.Companion.position
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class MusicDiscFragment : Fragment() {

    private var view: View? = null

    private var objectAnnotation: ObjectAnimator? = null
    private var position: Int = 0
    private var listSongs: ArrayList<Song> = arrayListOf()
    private var isCheckMusicDisc: Boolean = false

    companion object {
        private var imgCircle: CircleImageView? = null
        private var tvTiTle: TextView? = null
        fun updatePosition(index: Int) {
//            tvTiTle?.text = listSongs[index].songName
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_music_disc, container, false)

        position = PlaySongActivity.position
        listSongs = PlaySongActivity.listSongs

        Log.d("BBB", position.toString())
        Log.d("BBB", listSongs.toString())

        initView()

        Picasso.get().load(listSongs[position].imageSong).into(imgCircle)

        return view
    }

    private fun initView() {
        imgCircle = view?.findViewById(R.id.img_circle)
        tvTiTle = view?.findViewById(R.id.tv_title_music_disc)
        objectAnnotation = ObjectAnimator.ofFloat(imgCircle, "rotation", 0f, 360f)
        objectAnnotation?.duration = 10000
        objectAnnotation?.repeatCount = ValueAnimator.INFINITE
        objectAnnotation?.repeatMode = ValueAnimator.RESTART
        objectAnnotation?.interpolator = LinearInterpolator()
        objectAnnotation?.start()
    }


}
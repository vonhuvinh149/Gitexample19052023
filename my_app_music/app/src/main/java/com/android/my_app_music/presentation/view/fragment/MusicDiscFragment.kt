package com.android.my_app_music.presentation.view.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.my_app_music.R
import com.android.my_app_music.data.model.Song
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class MusicDiscFragment(
    private val position: Int,
    private val listSongs: ArrayList<Song>
) : Fragment() {

    private var view: View? = null

    private var objectAnnotation: ObjectAnimator? = null

    private var imgCircle: CircleImageView? = null
    private var tvTiTle: TextView? = null
    private var tvSingerName: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_music_disc, container, false)

        initView()

        return view
    }

    fun updateView(position: Int) {
        if (listSongs.size > 0) {
            tvTiTle?.text = listSongs[position].songName
            tvSingerName?.text = listSongs[position].singerName
            Picasso.get().load(listSongs[position].imageSong).into(imgCircle)
        }
    }

    private fun initView() {
        imgCircle = view?.findViewById(R.id.img_circle)
        tvTiTle = view?.findViewById(R.id.tv_title_music_disc)
        tvSingerName = view?.findViewById(R.id.tv_singer_name_disc)

        if (listSongs.size > 0) {
            tvTiTle?.text = listSongs[position].songName
            tvSingerName?.text = listSongs[position].singerName
            Picasso.get().load(listSongs[position].imageSong).into(imgCircle)
        }

        objectAnnotation = ObjectAnimator.ofFloat(imgCircle, "rotation", 0f, 360f)
        objectAnnotation?.duration = 10000
        objectAnnotation?.repeatCount = ValueAnimator.INFINITE
        objectAnnotation?.repeatMode = ValueAnimator.RESTART
        objectAnnotation?.interpolator = LinearInterpolator()
        objectAnnotation?.start()
    }


}
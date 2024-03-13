package com.android.my_app_music.presentation.view.activity

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.android.my_app_music.R
import com.android.my_app_music.data.SongSharedPreference

class SplashActivity : AppCompatActivity() {

    private lateinit var imgLottie: LottieAnimationView
    private lateinit var songSharedPreference: SongSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        songSharedPreference = SongSharedPreference(this)
        imgLottie = findViewById(R.id.img_lottie)

        imgLottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
    }
}
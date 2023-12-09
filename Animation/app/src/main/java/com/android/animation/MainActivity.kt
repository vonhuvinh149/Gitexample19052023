package com.android.animation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private var imgAlpha: ImageView? = null
    private var imgRotate: ImageView? = null
    private var imgScale: ImageView? = null
    private var imgSlate: ImageView? = null
    private var btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgAlpha = findViewById(R.id.img_anim_alpha)
        imgRotate = findViewById(R.id.img_anim_rotate)
        imgScale = findViewById(R.id.img_anim_scale)
        imgSlate = findViewById(R.id.img_anim_translate)
        btn = findViewById(R.id.btn_to_second)

        event()

    }

    private fun event() {

        val animAlpha: Animation = AnimationUtils.loadAnimation(this, R.anim.aim_alpha)
        val animRotate: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate)
        val animScale: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale)
        val animSlate: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_translate)

        imgAlpha?.setOnClickListener {
            imgAlpha?.startAnimation(animAlpha)
        }

        imgRotate?.setOnClickListener {
            imgRotate?.startAnimation(animRotate)
        }

        imgScale?.setOnClickListener {
            imgScale?.startAnimation(animScale)
        }
        imgSlate?.setOnClickListener {
            imgSlate?.startAnimation(animSlate)
        }

        btn?.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_intent_enter, R.anim.anim_intent_exit)
        }

    }
}
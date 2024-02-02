package com.android.activity_lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)
    }
}
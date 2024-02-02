package com.android.fragment_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    var btnLastFragment: Button? = null
    var btnFirstFragment: Button? = null

    val lastFragment = LastFragment()
    val firstFragment = FirstFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLastFragment = findViewById(R.id.btn_fr_1)
        btnFirstFragment = findViewById(R.id.btn_fr_2)

        binding(lastFragment)
        event()

    }

    private fun event() {
        btnLastFragment?.setOnClickListener {
            binding(lastFragment)
        }

        btnFirstFragment?.setOnClickListener {
            binding(firstFragment)
        }
    }

    private fun binding(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}
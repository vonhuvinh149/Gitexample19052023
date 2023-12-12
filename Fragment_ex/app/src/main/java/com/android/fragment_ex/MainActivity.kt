package com.android.fragment_ex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private var btnFirst: Button? = null
    private var btnLast: Button? = null
    private var secondActivity: Button? = null
    private var threeActivity: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFirst = findViewById(R.id.btn_first)
        btnLast = findViewById(R.id.btn_last)
        secondActivity = findViewById(R.id.second_activity)
        threeActivity = findViewById(R.id.three_activity)


        secondActivity?.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        }

        threeActivity?.setOnClickListener {
            val intent = Intent(this@MainActivity , ThreeActivity::class.java)
            startActivity(intent)
        }

    }


    fun addFragment(view: View) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment = Fragment()


        when (view.id) {
            R.id.btn_first -> {
                fragment = FistFragment()
            }

            R.id.btn_last -> {
                fragment = LastFragment()
            }
        }

        fragmentTransaction.replace(R.id.frame_fr, fragment)
        fragmentTransaction.commit()
    }
}
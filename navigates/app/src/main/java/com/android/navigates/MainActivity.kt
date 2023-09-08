package com.android.navigates

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private var btnNavigate: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNavigate?.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
//            intent.putExtra("aaa" , "vo nhu vinh")
//            intent.putExtra("qqq" , "diem quynh")
//            intent.putExtra("www" , "sen nguyen")
            startActivity(intent)
        }
    }
}
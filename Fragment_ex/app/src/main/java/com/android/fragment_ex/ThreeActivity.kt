package com.android.fragment_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ThreeActivity : AppCompatActivity() {

    private var btnAdd: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)

        btnAdd = findViewById(R.id.btn_add)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        btnAdd?.setOnClickListener {
            val fragmentC = FragmentC()
            val bundle = Bundle()
            bundle.putString("name" , "vo nhu vinh")
            fragmentC.arguments = bundle

            fragmentTransaction
                .add(R.id.linear_layout, fragmentC)
                .commit()
        }

    }
}
package com.android.fragment_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private var tvSecond : TextView? = null
    private var btnChange : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvSecond =findViewById(R.id.textView)
        btnChange = findViewById(R.id.btn_change)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentA = FragmentA()
        val fragmentB = FragmentB()

        fragmentTransaction.add(R.id.frag_a, fragmentA , "tag-a")
        fragmentTransaction.add(R.id.frag_b, fragmentB)
        fragmentTransaction.commit()


        btnChange?.setOnClickListener {
            fragmentA.bindingContent("change A")
        }
    }
}
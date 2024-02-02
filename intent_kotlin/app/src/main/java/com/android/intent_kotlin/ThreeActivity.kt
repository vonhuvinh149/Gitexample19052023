package com.android.intent_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)

        val intent = intent
        val bundle = intent.extras

        if (bundle != null) {
            Toast.makeText(
                this,
                "${bundle.getString("name")} - ${bundle.getInt("age")} - ${bundle.getDouble("weight")}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
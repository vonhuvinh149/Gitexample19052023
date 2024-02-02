package com.android.android_extensions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.android_extensions.databinding.ActivityMainBinding
import com.android.android_extensions.databinding.ActivityRadioBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRadioBinding

class RadioActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRadioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event()
    }

    private fun event() {

        var s = ""

        binding.btnTest.setOnClickListener {
            if (binding.radNam.isChecked) {
                s = binding.radNam.text.toString()
            } else if (binding.radNu.isChecked) {
                s = binding.radNu.text.toString()
            }

            if (s == ""){
                Toast.makeText(this , "Vui long chon gioi tinh" , Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this , "Gioi tinh cua ban la $s" , Toast.LENGTH_SHORT).show()
            }
        }
    }


}
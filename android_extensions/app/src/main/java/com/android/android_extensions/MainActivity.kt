package com.android.android_extensions

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.android_extensions.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {

    var lists : MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       event()
    }

    private fun event() {
        binding.btnTest.setOnClickListener {
            optionEvent()
        }

        binding.btnNavigationRadio.setOnClickListener {
            val intent = Intent(this , RadioActivity::class.java)
            startActivity(intent)
        }

        binding.btnNavigationImage.setOnClickListener {
            val intent = Intent(this , ImageActivity::class.java)
            startActivity(intent)
        }

        binding.btnNavigationListView.setOnClickListener {
            val intent = Intent(this , ListViewActivity::class.java)
            startActivity(intent)
        }

        binding.btnNavigationListView2.setOnClickListener {
            val intent = Intent(this , ListViewActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun optionEvent() {
        var str = ""
        if (binding.ckhMusic.isChecked){
            str += "${binding.ckhMusic.text} \n"
        }
        if (binding.ckhMovie.isChecked){
            str += "${binding.ckhMovie.text} \n"
        }
        if (binding.ckhShopping.isChecked){
            str += "${binding.ckhShopping.text} \n"
        }
        if (binding.ckhSoccer.isChecked){
            str += "${binding.ckhSoccer.text} \n"
        }
        binding.edtTest.setText(str)
    }
}
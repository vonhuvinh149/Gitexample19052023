package com.android.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var  activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        val mainViewModel :  MainViewModel = MainViewModel("vo nhu vinh android kotlin" , "Vonhuvinh149@gmail.com")

        activityMainBinding.mainViewModel = mainViewModel

        setContentView(activityMainBinding.root)
    }
}
package com.android.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mutableLiveDataString = MutableLiveData<String>()

        mutableLiveDataString.observe(this@MainActivity) {
            Log.d("BBB", it)
        }

        // write
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            mutableLiveDataString.value = "new data $count"
        }
    }
}
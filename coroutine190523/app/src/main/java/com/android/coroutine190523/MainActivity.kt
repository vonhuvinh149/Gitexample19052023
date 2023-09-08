package com.android.coroutine190523

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->

        }

        CoroutineScope(
            CoroutineName("coroutine 1") + Dispatchers.IO +
                    Job() + coroutineExceptionHandler
        ).launch {
//            val deferred1 = async {
//                delay(2000)
//                return@async 100
//            }
//            val deferred2 = async {
//                delay(1000)
//                return@async 100
//            }
//
//            val number1 =deferred1.await()
//            val number2 =deferred2.await()
//            Log.d("bbb",(number1 + number2).toString())

           val check =
            Log.d("bbb" , "next")

        }
    }
}

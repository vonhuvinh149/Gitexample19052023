package com.android.spinner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var spinner: Spinner? = null
    var list: Array<String> = arrayOf()
    var btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.sp_test)
        btn = findViewById(R.id.btn_navigation_spinner_2)

        setupSpinner()
        event()
    }

    private fun event() {

        btn?.setOnClickListener {
            val intent = Intent(this@MainActivity, SpinnerActivity::class.java)
            startActivity(intent)
        }

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "ban chon : ${list[position]}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setupSpinner() {
        list = resources.getStringArray(R.array.arr_string_test)
        spinner?.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            list
        )
    }
}
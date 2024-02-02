package com.android.autocomplatetextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class MainActivity : AppCompatActivity() {

    var autoTinhThanh : AutoCompleteTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lists = resources.getStringArray(R.array.arr_string_data)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            lists
        )

        autoTinhThanh?.setAdapter(adapter)
    }
}
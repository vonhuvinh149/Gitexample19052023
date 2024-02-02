package com.android.android_extensions

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.annotation.RequiresApi

class ListViewActivity : AppCompatActivity() {

    var listView: ListView? = null
    var phoneNumberLists: MutableList<String> = mutableListOf()
    var listViewTodo: ListView? = null
    var btnCreate: Button? = null
    var edtTest: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        listView = findViewById(R.id.lv_product)
        listViewTodo = findViewById(R.id.lv_todo)
        btnCreate = findViewById(R.id.btn_create)
        edtTest = findViewById(R.id.edt_test)

        val color = Color.parseColor("#CCFFFF")
        listView?.setBackgroundColor(color)

        var arrString = resources.getStringArray(R.array.arrStringProductName)

        listView?.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arrString
        )

        observer()
        event()
        createAdapter()

    }

    private fun observer() {
        phoneNumberLists.add("0987652421")
        phoneNumberLists.add("0324325256")
        phoneNumberLists.add("0913214124")
        phoneNumberLists.add("0623486345")
        phoneNumberLists.add("2432526246")
    }

    private fun event() {
        btnCreate?.setOnClickListener {
            if (edtTest?.text.toString().trim().isNotBlank()){
                phoneNumberLists.add(edtTest?.text.toString())
                edtTest?.setText("")
                createAdapter()
            }
        }
    }

    private fun createAdapter(){
        listViewTodo?.adapter = ArrayAdapter(
            this ,
            android.R.layout.simple_list_item_1 ,
            phoneNumberLists
        )
        Log.d("BBB" ,"check")
    }
}
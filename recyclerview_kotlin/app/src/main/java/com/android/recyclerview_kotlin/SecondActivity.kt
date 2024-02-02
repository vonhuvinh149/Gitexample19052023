package com.android.recyclerview_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SecondActivity : AppCompatActivity() {

    var recyclerview: RecyclerView? = null
    lateinit var customAdapter: CustomAdapter
    var lists: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        
        lists.add(R.drawable.img)
        lists.add(R.drawable.img_2)
        lists.add(R.drawable.img_3)
        lists.add(R.drawable.img_4)
        lists.add(R.drawable.img_5)
        lists.add(R.drawable.img)
        lists.add(R.drawable.img_2)
        lists.add(R.drawable.img_3)
        lists.add(R.drawable.img_4)
        lists.add(R.drawable.img_5)

        recyclerview = findViewById(R.id.recyclerview_2)
        customAdapter = CustomAdapter(lists)

        recyclerview?.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerview?.adapter = customAdapter

    }
}
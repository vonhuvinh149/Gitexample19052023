package com.android.recyclerview_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    lateinit var girlAdapter: GirlAdapter
    var lists: MutableList<Girl> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()
        observer()

        val btnSecondActivity :Button= findViewById(R.id.btn_navigation)
        btnSecondActivity.setOnClickListener {
            val intent = Intent(this , SecondActivity::class.java)
            startActivity(intent)
        }

        girlAdapter = GirlAdapter(lists, object : OnClickItem {
            override fun onClickItem(position: Int) {
                Toast.makeText(this@MainActivity , "${lists[position].name}" , Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView?.adapter = girlAdapter
        recyclerView?.layoutManager = GridLayoutManager(
            this,
            3,
            GridLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerview)
    }

    private fun observer() {
        lists.add(Girl("Nguyen thi minh khai 1", R.drawable.img))
        lists.add(Girl("Nguyen thi minh khai 2", R.drawable.img_1))
        lists.add(Girl("Nguyen thi minh khai 3", R.drawable.img_2))
        lists.add(Girl("Nguyen thi minh khai 4", R.drawable.img_3))
        lists.add(Girl("Nguyen thi minh khai 5", R.drawable.img_4))
        lists.add(Girl("Nguyen thi minh khai 6", R.drawable.img_5))
    }
}
package com.android.layout_login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {

    private var listUser: MutableList<User>? = null
    private var recyclerView: RecyclerView? = null
    private var userAdapter: UserAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        recyclerView = findViewById(R.id.recyclerView)

        listUser = mutableListOf<User>().apply {
            add(User("vo nhu vinh", "qua hoa haoo", "R.drawable.image1"))
            add(User("vo thi hau", "xinh", "R.drawable.image1"))
            add(User("nguyen nhu ngoc", "gioi", "R.drawable.image1"))
            add(User("doan ai duyen", "oke ka", "R.drawable.image1"))
            add(User("vo nhu vinh", "qua hoa haoo", "R.drawable.image1"))
        }
        userAdapter = UserAdapter(this@SecondActivity, listUser ?: mutableListOf())
        recyclerView?.adapter = userAdapter
    }
}

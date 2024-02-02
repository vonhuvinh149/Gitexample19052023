package com.android.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var girlAdapter: GirlAdapter
    var recyclerView: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        observerData()

        recyclerView?.adapter = girlAdapter
    }

    private fun observerData() {


        var listGirl = mutableListOf<Girl>()

        listGirl.addAll(
            listOf(
                Girl(R.drawable.img, "Girl 1"),
                Girl(R.drawable.img_1, "Girl 2"),
                Girl(R.drawable.img_2, "Girl 3"),
                Girl(R.drawable.img_3, "Girl 4"),
                Girl(R.drawable.img_4, "Girl 5"),
                Girl(R.drawable.img_5, "Girl 6"),
                Girl(R.drawable.img_1, "Girl 7"),
                Girl(R.drawable.img_2, "Girl 8")
            )
        )

        var listCategory = mutableListOf<Category>()

        listCategory.addAll(
            listOf(
                Category("Category 1", listGirl),
                Category("Category 1", listGirl),
                Category("Category 1", listGirl),
                Category("Category 1", listGirl),
                Category("Category 1", listGirl)
            )
        )
        categoryAdapter
        girlAdapter.setData(listGirl)
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recycler_view)
        categoryAdapter = CategoryAdapter(this)
        girlAdapter = GirlAdapter()
    }
}
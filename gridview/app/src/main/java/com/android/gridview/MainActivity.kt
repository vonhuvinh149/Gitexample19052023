package com.android.gridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var customAdapter: CustomAdapter
    var gridView: GridView? = null
    var lists: MutableList<Girl> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.grid_view)

        observer()
        event()

        customAdapter = CustomAdapter(this, lists)
        gridView?.adapter = customAdapter
    }

    private fun event() {
        gridView?.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                Toast.makeText(this, lists[position].name, Toast.LENGTH_SHORT).show()
            }
    }

    private fun observer() {
        lists.add(Girl(R.drawable.img, "Nguyen thi tra"))
        lists.add(Girl(R.drawable.img_1, "Tran thi hanh"))
        lists.add(Girl(R.drawable.img_2, "Nguyen thi Trinh"))
        lists.add(Girl(R.drawable.img_3, "Bui Kim Anh"))
        lists.add(Girl(R.drawable.img, "Nguyen thi tra"))
        lists.add(Girl(R.drawable.img_1, "Tran thi hanh"))
        lists.add(Girl(R.drawable.img_2, "Nguyen thi Trinh"))
        lists.add(Girl(R.drawable.img_3, "Bui Kim Anh"))
        lists.add(Girl(R.drawable.img, "Nguyen thi tra"))
        lists.add(Girl(R.drawable.img_1, "Tran thi hanh"))
        lists.add(Girl(R.drawable.img_2, "Nguyen thi Trinh"))
        lists.add(Girl(R.drawable.img_3, "Bui Kim Anh"))
        lists.add(Girl(R.drawable.img, "Nguyen thi tra"))
        lists.add(Girl(R.drawable.img_1, "Tran thi hanh"))
        lists.add(Girl(R.drawable.img_2, "Nguyen thi Trinh"))
        lists.add(Girl(R.drawable.img_3, "Bui Kim Anh"))
    }
}
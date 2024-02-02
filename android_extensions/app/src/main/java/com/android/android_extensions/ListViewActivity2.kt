package com.android.android_extensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast

class ListViewActivity2 : AppCompatActivity() {

    var listViewGirl: ListView? = null
    var listGirls: MutableList<Girl> = mutableListOf()
    lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view2)

        listViewGirl = findViewById(R.id.list_view_girl)

        observer()
        event()

        customAdapter = CustomAdapter(this, listGirls)
        listViewGirl?.adapter = customAdapter
    }

    private fun event() {
        listViewGirl?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                Toast.makeText(
                    this,
                    "Ban chon ${listGirls[position].name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun observer() {
        listGirls.add(Girl("Nguyen Thi Tra My", 26, R.drawable.img))
        listGirls.add(Girl("Tran Khanh Thi", 22, R.drawable.img_1))
        listGirls.add(Girl("Bui Phuong Uyen", 26, R.drawable.img_2))
        listGirls.add(Girl("Nguyen Thi Hai An", 26, R.drawable.img_3))
        listGirls.add(Girl("Nguyen Thi Thu Huong", 22, R.drawable.img_4))
        listGirls.add(Girl("Ho Thi Bich Ngoc", 26, R.drawable.img_5))
        listGirls.add(Girl("Nguyen Thi Tra My", 26, R.drawable.img))
        listGirls.add(Girl("Tran Khanh Thi", 22, R.drawable.img_1))
        listGirls.add(Girl("Bui Phuong Uyen", 26, R.drawable.img_2))
        listGirls.add(Girl("Nguyen Thi Hai An", 26, R.drawable.img_3))
        listGirls.add(Girl("Nguyen Thi Thu Huong", 22, R.drawable.img_4))
        listGirls.add(Girl("Ho Thi Bich Ngoc", 26, R.drawable.img_5))
    }
}
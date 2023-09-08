package com.android.intentimplicit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ListAnimalActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var listImageResources = mutableListOf<Int>()
    private var animalsAdapter: AnimalAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_animal)

        recyclerView = findViewById(R.id.recycler_view)
        
        listImageResources.clear()
        val listImageNames = intent.getStringArrayExtra("list_image")
        listImageNames?.forEach {
            val imageResource = resources.getIdentifier(it, "drawable", packageName)
            listImageResources.add(imageResource)
        }
        animalsAdapter = AnimalAdapter(listImageResources)
        recyclerView?.adapter = animalsAdapter

        animalsAdapter?.setOnClickListener(object : OnAnimalClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(this@ListAnimalActivity, MainActivity::class.java)
                intent.putExtra("image_resource_id", listImageResources.getOrNull(position))
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }
}



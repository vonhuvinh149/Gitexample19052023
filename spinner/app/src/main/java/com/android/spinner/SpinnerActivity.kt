package com.android.spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class SpinnerActivity : AppCompatActivity() {

    var imgPhone: ImageView? = null
    var tvName: TextView? = null
    var lists: MutableList<Phone> = mutableListOf()
    lateinit var customSpinner: CustomSpinner
    var spPhone: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)

        imgPhone = findViewById(R.id.img_phone)
        tvName = findViewById(R.id.tv_name)
        spPhone = findViewById(R.id.sp_phone)


        observerData()
        setupCustomSpinner()
        event()

    }

    private fun event() {
        spPhone?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@SpinnerActivity, lists[position].name, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setupCustomSpinner() {
        customSpinner = CustomSpinner(this, lists)
        spPhone?.adapter = customSpinner
    }

    private fun observerData() {
        lists.add(Phone("Iphone", R.drawable.img))
        lists.add(Phone("Xiaomi", R.drawable.img_1))
        lists.add(Phone("SamSung", R.drawable.img_2))
        lists.add(Phone("Oppo", R.drawable.img_3))
    }
}
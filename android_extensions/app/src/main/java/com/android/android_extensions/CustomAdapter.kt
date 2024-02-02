package com.android.android_extensions

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(
    val activity: Activity,
    val list: MutableList<Girl>
) : ArrayAdapter<Girl>(activity, R.layout.layout_item_girl) {

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val context = activity.layoutInflater
        val rowView = context.inflate(R.layout.layout_item_girl, parent, false)

        val image: ImageView = rowView.findViewById(R.id.img_item)
        val tvName: TextView = rowView.findViewById(R.id.tv_name)
        val tvAge: TextView = rowView.findViewById(R.id.tv_age)

        tvName.text = list[position].name
        tvAge.text = "${list[position].age} tuoi"
        image.setImageResource(list[position].img)

        return rowView
    }
}
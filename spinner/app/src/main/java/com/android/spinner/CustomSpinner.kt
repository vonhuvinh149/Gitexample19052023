package com.android.spinner

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomSpinner(
    val activity: Activity, val lists: MutableList<Phone>
) : ArrayAdapter<Phone>(activity, R.layout.layout_sp_item) {

    override fun getCount(): Int = lists.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        return initView(position, convertView, parent)

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val context = activity.layoutInflater
        val rowView = context.inflate(R.layout.layout_sp_item, parent, false)

        val image = rowView.findViewById<ImageView>(R.id.img_phone)
        val name = rowView.findViewById<TextView>(R.id.tv_name)

        image.setImageResource(lists[position].img)
        name.text = lists[position].name

        return rowView
    }
}
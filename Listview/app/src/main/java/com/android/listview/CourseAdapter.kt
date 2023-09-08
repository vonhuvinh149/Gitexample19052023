package com.android.listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CourseAdapter(var context: Context, var listCourse: ArrayList<Course>) : BaseAdapter() {

    inner class CourseViewHolder(row: View) {
        var imgLogo: ImageView
        var courseName: TextView
        var desc: TextView

        init {
            courseName = row.findViewById(R.id.courseName)
            desc = row.findViewById(R.id.desc)
            imgLogo = row.findViewById(R.id.logoCourse)
        }
    }

    override fun getCount() = listCourse.size

    override fun getItem(position: Int): Any {
        return listCourse.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        var view: View
        var viewHolder: CourseViewHolder
        if (convertView == null) {
            var layoutInflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.layout_item_course, null)
            viewHolder = CourseViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as CourseViewHolder
        }

        var course: Course = getItem(position) as Course
        viewHolder.courseName.text = course.name
        viewHolder.desc.text = course.desc
        viewHolder.imgLogo.setImageResource(course.img)
        return view
    }
}
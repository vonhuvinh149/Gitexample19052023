package com.android.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(
    var context: Context,
    var listCourse: MutableList<Course>
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtCourseName: TextView = view.findViewById(R.id.displayCourseName)
        private var txtDesc: TextView = view.findViewById(R.id.displayDesc)
        private var imageDelete: ImageView = view.findViewById(R.id.imgDelete)

        fun bind(course: Course) {
            this.txtCourseName.text = course.courseName
            this.txtDesc.text = course.desc
        }

        imageDelete.setOnClickListener {
            init {
                onClickListener?.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount() = listCourse.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(listCourse[position])
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
package com.android.fragmentchangeorientation

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<Student> = mutableListOf()
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var onClickItemStudent: OnClickStudentItem? = null

    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameStudent: TextView = view.findViewById(R.id.student_name)
        private val img: ImageView = view.findViewById(R.id.student_img)
        private val btnItem: LinearLayout = view.findViewById(R.id.student_item)

        fun bind(student: Student) {
            nameStudent.text = student.name
            img.setImageResource(student.image)
        }


        init {
            btnItem.setOnClickListener {
                onClickItemStudent?.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    fun updateData(studentList: MutableList<Student>) {
        if (students.size > 0) {
            students.clear()
        }
        students.addAll(studentList)
    }

    fun getListStudents() : List<Student>{
        return students
    }

    fun setOnClickStudentItem(onClickStudentItem: OnClickStudentItem) {
        this.onClickItemStudent = onClickStudentItem
    }

    interface OnClickStudentItem {
        fun onClick(position: Int)
    }
}
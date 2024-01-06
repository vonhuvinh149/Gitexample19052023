package com.android.fragmentchangeorientation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FragmentStudentList : Fragment() {

    private var listStudents: MutableList<Student> = mutableListOf()
    private lateinit var adapterStudent: StudentAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var sendStudent: ISendStudent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

        sendStudent = activity as ISendStudent

        listStudents.add(Student("vo nhu vinh", R.drawable.img1))
        listStudents.add(Student("Nguyen thu huong", R.drawable.img2))
        listStudents.add(Student("tran ba on", R.drawable.img1))
        listStudents.add(Student("Nguyen thu huong", R.drawable.img2))
        listStudents.add(Student("tran ba on", R.drawable.img1))
        listStudents.add(Student("vo nhu vinh", R.drawable.img1))
        listStudents.add(Student("Nguyen thu huong", R.drawable.img2))
        listStudents.add(Student("tran ba on", R.drawable.img1))
        listStudents.add(Student("Nguyen thu huong", R.drawable.img2))
        listStudents.add(Student("tran ba on", R.drawable.img1))

        recyclerView = view.findViewById(R.id.recycler_view)
        adapterStudent = StudentAdapter()
        adapterStudent.updateData(listStudents)
        recyclerView?.adapter = adapterStudent

        adapterStudent.setOnClickStudentItem(object : StudentAdapter.OnClickStudentItem {
            override fun onClick(position: Int) {
                val student: Student = adapterStudent.getListStudents()[position]
                sendStudent.student(student)
            }
        })

        return view
    }
}
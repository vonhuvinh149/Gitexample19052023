package com.android.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var listCourse: MutableList<Course>? = null
    private var recyclerView: RecyclerView? = null
    private var courseAdapter: CourseAdapter? = null
    private var editCourseName: EditText? = null
    private var editDesc: EditText? = null
    private var btnSave: Button? = null
    private var btnClear: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        editCourseName = findViewById(R.id.editCourseName)
        editDesc = findViewById(R.id.editDesc)
        btnSave = findViewById(R.id.btnSave)
        btnClear = findViewById(R.id.btnClear)

        listCourse = mutableListOf()

        btnSave?.setOnClickListener {
            val courName: String = editCourseName?.getText().toString()
            val description: String = editDesc?.getText().toString()
            if (courName.isEmpty() || description.isEmpty()){
                return@setOnClickListener
            }
            listCourse?.addAll(listOf(Course(courName , description)))
            courseAdapter = CourseAdapter(this@MainActivity, listCourse ?: mutableListOf())
            editCourseName?.text = null
            editDesc?.text = null
            recyclerView?.adapter = courseAdapter
        }

        btnClear?.setOnClickListener {
            listCourse?.clear()
            courseAdapter?.notifyDataSetChanged()
        }

        courseAdapter?.setOnClickListener(object : CourseAdapter.OnClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Đã xoá ${listCourse!![position].courseName}",
                    Toast.LENGTH_SHORT
                ).show()
                listCourse?.removeAt(position)
                courseAdapter?.notifyItemRemoved(position)
            }
        })

    }
}
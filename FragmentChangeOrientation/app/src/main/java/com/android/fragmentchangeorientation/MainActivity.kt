package com.android.fragmentchangeorientation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity(), ISendStudent {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val studentFragment = FragmentStudentList()
        fragmentTransaction.add(R.id.content, studentFragment)
        fragmentTransaction.commit()
    }

    override fun student(student: Student) {
        val fragmentDetailStudent : FragmentDetailStudent = supportFragmentManager.findFragmentById(R.id.content_detail) as FragmentDetailStudent
        fragmentDetailStudent.setInfo(student)
    }
}
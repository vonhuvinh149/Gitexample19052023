package com.android.fragmentchangeorientation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentDetailStudent : Fragment() {

    private var tvName: TextView? = null
    private var imgStudent: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_student, container, false)
        tvName = view.findViewById(R.id.name_detail)
        imgStudent = view.findViewById(R.id.img_detail)
        return view
    }

     fun setInfo(student: Student) {
        Log.d("BBB" , student.toString())
        tvName?.text = student.name
        imgStudent?.setImageResource(student.image)
    }

}
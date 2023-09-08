package com.android.listview

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var listview: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listview = findViewById(R.id.listview)

        var arrCourse: ArrayList<Course> = ArrayList()

        arrCourse.add(Course("java", "write one run anyware", R.drawable.img))
        arrCourse.add(Course("php", "Hypertext Preprocessor", R.drawable.img_1))
        arrCourse.add(Course("js", "javascript", R.drawable.img_2))
        arrCourse.add(Course("python", "Guido van RossumGuido van Rossum", R.drawable.img_3))
        arrCourse.add(Course("java", "write one run anyware", R.drawable.img))
        arrCourse.add(Course("php", "Hypertext Preprocessor", R.drawable.img_1))
        arrCourse.add(Course("js", "javascript", R.drawable.img_2))
        arrCourse.add(Course("python", "Guido van Rossum", R.drawable.img_3))

        listview?.adapter = CourseAdapter(this@MainActivity, arrCourse)

    }

}
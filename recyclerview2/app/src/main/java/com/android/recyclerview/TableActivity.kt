package com.android.recyclerview

import android.graphics.Color
import android.icu.text.ListFormatter.Width
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast

class TableActivity : AppCompatActivity() {

    private var tableLayout: TableLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        tableLayout = findViewById(R.id.tableLayout)

        val list = listOf<Student>(
            Student(1, "Võ Như A", 3),
            Student(1, "Võ Như B", 3),
            Student(1, "Võ Như C", 3),
            Student(1, "Võ Như D", 3),
            Student(1, "Võ Như E", 3),
        )

        val titleRow = TableRow(this)

        val tvIdTitle = TextView(this)
        val tvNameTitle = TextView(this)
        val tvAgeTitle = TextView(this)

        bind(tvIdTitle, "Stt" ,titleRow)
        bind(tvNameTitle, "Tên học sinh" ,titleRow)
        bind(tvAgeTitle, "Tuổi",titleRow)

        tableLayout?.addView(titleRow)

        for (item in list) {
            val tableRow = TableRow(this)
            val tvId = TextView(this)
            bind(tvId, item.id.toString() ,tableRow)

            val tvName = TextView(this)
            bind(tvName, item.name ,tableRow)

            val tvAge = TextView(this)
            bind(tvAge, item.age.toString() , tableRow)

            tableLayout?.addView(tableRow)
        }
    }

    private fun bind(textView: TextView, str: String , tableRow: TableRow) {
        textView.setPadding(16, 8, 16, 8)
        textView.text = str
        textView.layoutParams = TableRow.LayoutParams(
            0 ,
            TableRow.LayoutParams.WRAP_CONTENT ,
            1f
        )
        textView.gravity =Gravity.CENTER
        textView.setBackgroundResource(R.drawable.border_shape)

        textView.setOnClickListener {
            Toast.makeText(this , "$str" , Toast.LENGTH_SHORT).show()
        }

        tableRow.addView(textView)
    }
}
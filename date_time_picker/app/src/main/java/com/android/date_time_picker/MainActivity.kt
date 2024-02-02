package com.android.date_time_picker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    val dateTime = Calendar.getInstance()
    var btnTime: ImageButton? = null
    var btnDate: ImageButton? = null
    var tvTime: TextView? = null
    var tvDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        event()
    }

    private fun event() {
        val startHour = dateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = dateTime.get(Calendar.MINUTE)
        val startDay = dateTime.get(Calendar.DATE)
        val startMonth = dateTime.get(Calendar.MONTH)
        val startYear = dateTime.get(Calendar.YEAR)

        btnTime?.setOnClickListener {
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                tvTime?.text = "$hourOfDay : $minute"
            }, startHour, startMinute, false).show()
        }

        btnDate?.setOnClickListener {
            Log.d("BBB" , startDay.toString())
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    tvDate?.text = "$dayOfMonth-$month-$year"
                }, startYear, startMonth, startDay).show()
        }
    }

    private fun initView() {
        btnTime = findViewById(R.id.btn_time)
        btnDate = findViewById(R.id.btn_date)
        tvTime = findViewById(R.id.tv_time)
        tvDate = findViewById(R.id.tv_date)
    }
}
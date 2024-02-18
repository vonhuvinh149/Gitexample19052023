package com.android.crud_sqlite

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    lateinit var rs: Cursor
    lateinit var adapter: SimpleCursorAdapter
    var edtUsername: EditText? = null
    var edtPassword: EditText? = null
    var btnFirst: Button? = null
    var btnNext: Button? = null
    var btnPrev: Button? = null
    var btnLast: Button? = null
    var btnSave: Button? = null
    var btnUpdate: Button? = null
    var btnDelete: Button? = null
    var btnClear: Button? = null
    var btnViewALl: Button? = null
    var lvItem: ListView? = null
    var searchView: androidx.appcompat.widget.SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        event()

        var helper = MyDBHelper(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery("SELECT * FROM USERS LIMIT 20 ", null)

//        adapter = SimpleCursorAdapter(
//            applicationContext,
//            android.R.layout.simple_expandable_list_item_2,
//            rs,
//            arrayOf("USERNAME", "PASSWORD"),
//            intArrayOf(android.R.id.text1, android.R.id.text2),
//            0
//        )
//
//        lvItem?.adapter = adapter

    }

    private fun event() {
        btnFirst?.setOnClickListener {
            if (rs.moveToFirst()) {
                edtUsername?.setText(rs.getString(1))
                edtPassword?.setText(rs.getString(2))
            }
        }

        btnLast?.setOnClickListener {
            if (rs.moveToLast()) {
                edtUsername?.setText(rs.getString(1))
                edtPassword?.setText(rs.getString(2))
            }
        }

        btnNext?.setOnClickListener {
            if (rs.moveToNext()) {
                edtUsername?.setText(rs.getString(1))
                edtPassword?.setText(rs.getString(2))
            }
        }

        btnPrev?.setOnClickListener {
            if (rs.moveToPrevious()) {
                edtUsername?.setText(rs.getString(1))
                edtPassword?.setText(rs.getString(2))
            }
        }

        btnSave?.setOnClickListener {

        }

        btnUpdate?.setOnClickListener {

        }

        btnDelete?.setOnClickListener {

        }

        btnClear?.setOnClickListener {

        }

        btnViewALl?.setOnClickListener {
            lvItem?.visibility = View.VISIBLE
            searchView?.visibility = View.VISIBLE
//            adapter.notifyDataSetChanged()
            searchView?.queryHint = "Tim kiem trong ${rs.count} bang ghi"
        }
    }

    private fun initView() {
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_pwd)
        btnFirst = findViewById(R.id.btn_first)
        btnNext = findViewById(R.id.btn_next)
        btnPrev = findViewById(R.id.btn_prev)
        btnLast = findViewById(R.id.btn_last)
        btnSave = findViewById(R.id.btn_add)
        btnUpdate = findViewById(R.id.btn_update)
        btnDelete = findViewById(R.id.btn_delete)
        btnClear = findViewById(R.id.btn_clear)
        btnViewALl = findViewById(R.id.btn_view_all)
        lvItem = findViewById(R.id.lv_item)
        searchView = findViewById(R.id.serach_view)

    }
}
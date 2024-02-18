package com.android.sqlite_kotlin

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var edtName: EditText? = null
    var edtAge: EditText? = null
    var btnSave: Button? = null

    private var dbCopy :CopyHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtName = findViewById(R.id.edt_name)
        edtAge = findViewById(R.id.edt_age)
        btnSave = findViewById(R.id.btn_save)

        // copy data tu file
        dbCopy = CopyHelper(this)
        dbCopy?.openDataBase()


        var helper = MyDbHelper(applicationContext)
        val db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM CUSTOMER ;", null)
        if (rs.moveToLast()) {
            Toast.makeText(this, "${rs.getString(1)}", Toast.LENGTH_SHORT).show()
        }

        btnSave?.setOnClickListener {
            var cv = ContentValues()
            cv.put("NAME", edtName?.text.toString())
            cv.put("AGE", edtAge?.text.toString().toInt())
            db.insert("CUSTOMER", null, cv )
            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show()
            edtName?.setText("")
            edtAge?.setText("")
            edtName?.requestFocus()
        }
    }
}
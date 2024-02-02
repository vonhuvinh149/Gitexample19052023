package com.android.alertdialog

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var btnExit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnExit = findViewById(R.id.btn_open_alert)

        btnExit?.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setTitle("confirm window")
                setMessage("Do you want to close this app")
                setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                setPositiveButton("yes") { dialogInterface: DialogInterface, i: Int ->
                    finish()
                }
                setCancelable(false)
            }
            dialog.show()
        }
    }
}
package com.android.alertdialog

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import com.android.alertdialog.databinding.LayoutCustomDialogBinding

class SecondActivity : AppCompatActivity() {

    var btnClose: ImageButton? = null
    var btnDialog: Button? = null
    var btnTest: Button? = null
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        btnTest = findViewById(R.id.btn_test)

        btnTest?.setOnClickListener {
            showNormal()
        }

    }

    private fun showNormal() {
        val build = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.layout_custom_dialog, null)

        build.setView(view)

        btnClose = view.findViewById(R.id.btn_close)
        btnDialog = view.findViewById(R.id.btn_dialog)

        btnClose?.setOnClickListener {
            Log.d("BBB", "hello")
            dialog.dismiss()
        }
        btnDialog?.setOnClickListener {
            finish()
        }
        dialog = build.create()
        dialog.setCancelable(false)
        dialog.show()
    }
}
package com.android.sharepreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editUserName: EditText
    private lateinit var editPassword: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var btnLogin: Button
    private lateinit var displayText: TextView
    private lateinit var iconDelete: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editUserName = findViewById(R.id.userName)
        editPassword = findViewById(R.id.password)
        checkBox = findViewById(R.id.checkbox)
        btnLogin = findViewById(R.id.btnLogin)
        displayText = findViewById(R.id.display)
        iconDelete = findViewById(R.id.delete)


        // Yeu cau
        // 1 - Nếu đăng nhập thành công và có chọn vào check box thì lưu tài khoản
        // 2 - Khi mở lại app nếu có dữ liệu thì hiển thị cho phần input và
        // 3- Thực hiện xoá dữ liệu
        //    - Khi người dùng đăng nhập nhưng không chọn vào check box
        //    - Khi click icon delete của output

        btnLogin.setOnClickListener {
            val userName = editUserName.text.toString()
            val password = editPassword.text.toString()
            val checked = checkBox.isChecked ?: false

            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "input valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userName == "vonhuvinh149@gmail.com" && password == "khongten123") {
                Toast.makeText(this@MainActivity, "input success", Toast.LENGTH_SHORT).show()
                if (checked){

                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "tên đăng nhập hoặc mk không chính xác",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
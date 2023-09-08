package com.android.layout_login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var editEmail: EditText? = null
    private var editPassword: EditText? = null
    private var checkbox: CheckBox? = null
    private var btnLogin: Button? = null
    private var btnRegister: TextView? = null
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editEmail = findViewById(R.id.edtEmail)
        editPassword = findViewById(R.id.edtPassword)
        checkbox = findViewById(R.id.checkboxRemember)
        btnLogin = findViewById(R.id.loginBtn)
        btnRegister = findViewById(R.id.btnRegister)

        sharedPreferences = getSharedPreferences("app_list_girl", MODE_PRIVATE)
        editor = sharedPreferences?.edit()

//        sharedPreferences.let {
//            val emailPreferences = it?.getString("email","")
//            val passwordPreferences = it?.getString("password","")
//            editEmail?.setText(emailPreferences.toString())
//            editPassword?.setText(passwordPreferences)
//        }


        val emailPreferences = sharedPreferences?.getString("email", "") ?: ""
        val passwordPreferences = sharedPreferences?.getString("password", "") ?: ""
        val isCheckedPreferences = sharedPreferences?.getBoolean("isChecked", false)

        if (isCheckedPreferences == true){
            editEmail?.setText(emailPreferences)
            editPassword?.setText(passwordPreferences)
            checkbox?.isChecked = isCheckedPreferences
        }

        btnLogin?.setOnClickListener {
            val email: String = editEmail?.text.toString().trim()
            val password: String = editPassword?.text.toString().trim()
            val checked = checkbox?.isChecked == true
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Pls input", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (checked){
                editor?.putBoolean("isChecked", true)
                editor?.apply()
            }else {
                editor?.remove("isChecked")
                editor?.apply()
            }

            if (email != emailPreferences || password != passwordPreferences) {
                Toast.makeText(this@MainActivity, "login fail", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "login success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivity(intent)
            }


        }

        btnRegister?.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
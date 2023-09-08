package com.android.layout_login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private var rgEmail: EditText? = null
    private var rgPassword: EditText? = null
    private var confirmPassword: EditText? = null
    private var btnRegister: Button? = null
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        rgEmail = findViewById(R.id.edtEmail)
        rgPassword = findViewById(R.id.edtPassword)
        confirmPassword = findViewById(R.id.edtConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        sharedPreferences = getSharedPreferences("app_list_girl", MODE_PRIVATE)
        editor = sharedPreferences?.edit()

        btnRegister?.setOnClickListener {

            val email = rgEmail?.text.toString().trim()
            val password = rgPassword?.text.toString().trim()
            val confirmPassword = confirmPassword?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Input pls", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (confirmPassword != password) {
                Toast.makeText(this@RegisterActivity, "Input Valid", Toast.LENGTH_SHORT).show()
            } else {
                editor?.putString("email", email)
                editor?.putString("password", password)
                editor?.apply()
                Toast.makeText(this@RegisterActivity, "Input success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
package com.android.my_app_music.presentation.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.my_app_music.R
import com.android.my_app_music.common.Validation
import com.android.my_app_music.utils.SpannedUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private var btnLogin: Button? = null
    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var tvNavRegister: TextView? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        event()
    }

    private fun event() {
        btnLogin?.setOnClickListener {
            val email = edtEmail?.text.toString()
            val password = edtPassword?.text.toString()
            if (isValid(email, password)) {
                login(email, password)
            } else {
                return@setOnClickListener
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    edtEmail?.setText("")
                    edtPassword?.setText("")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    edtPassword?.setText("")
                    Toast.makeText(
                        this,
                        "tên tài khoản hoặc mật khẩu không đúng",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun initView() {
        auth = Firebase.auth
        btnLogin = findViewById(R.id.btn_login)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        tvNavRegister = findViewById(R.id.tv_nav_regis)

        textNavRegister()
    }

    private fun isValid(email: String, password: String): Boolean {
        val msgEmail = Validation.isValidEmail(email)
        val msgPassword = Validation.isValidPassword(password)
        var flag = true

        if (msgEmail.isNotEmpty()) {
            edtEmail?.error = msgEmail
            flag = false
        }

        if (msgPassword.isNotEmpty()) {
            edtPassword?.error = msgPassword
            flag = false
        }
        return flag
    }

    private fun textNavRegister() {
        SpannableStringBuilder().apply {
            append("Don't have an account? ")
            append(
                SpannedUtils.setClickColorLink(
                    text = "Register",
                    context = this@LoginActivity,
                    onListenClick = onClickRegister
                )
            )
            tvNavRegister?.text = this
            tvNavRegister?.highlightColor = Color.BLUE
            tvNavRegister?.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private var onClickRegister = {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}
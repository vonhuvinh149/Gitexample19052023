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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private var btnRegister: Button? = null
    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var edtConfPassword: EditText? = null
    private lateinit var auth: FirebaseAuth
    private var tvNavLogin: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        event()
    }

    private fun event() {
        btnRegister?.setOnClickListener {
            val email: String = edtEmail?.text.toString()
            val password: String = edtPassword?.text.toString()
            val confPassword: String = edtConfPassword?.text.toString()
            if (isValid(email, password)) {
                if (password == confPassword) {
                    register(email, password)
                } else {
                    edtConfPassword?.error = "Mật khẩu không khớp"
                }
            } else {
                return@setOnClickListener
            }
        }
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

    private fun initView() {
        auth = Firebase.auth
        btnRegister = findViewById(R.id.btn_login_rg)
        edtEmail = findViewById(R.id.edt_email_rg)
        edtPassword = findViewById(R.id.edt_password_rg)
        edtConfPassword = findViewById(R.id.edt_conf_password)
        tvNavLogin = findViewById(R.id.tv_nav_login)

        displayTextViewRegister()
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    edtPassword?.setText("")
                    edtEmail?.setText("")
                    edtConfPassword?.setText("")
                    Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun displayTextViewRegister() {
        SpannableStringBuilder().apply {
            append("Already have an account? ")
            append(
                SpannedUtils.setClickColorLink(
                    text = "Sign In",
                    context = this@RegisterActivity,
                    onListenClick = onClickRegister
                )
            )
            tvNavLogin?.text = this
            tvNavLogin?.highlightColor = Color.BLUE
            tvNavLogin?.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private var onClickRegister = {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}